package ovh.eukon05.lokit.decisionservice.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import ovh.eukon05.lokit.decisionservice.exception.CardWithoutUserException;
import ovh.eukon05.lokit.decisionservice.exception.DeviceWithoutRoomException;
import ovh.eukon05.lokit.decisionservice.exception.TokenNotFoundException;

import java.util.*;

import static ovh.eukon05.lokit.decisionservice.cache.RedisCacheKeys.*;

@Component
@RequiredArgsConstructor
class DecisionCacheImpl implements DecisionCache {
    private final StringRedisTemplate redis;

    @Override
    public boolean isRoomActive(UUID roomId) {
        return redis.opsForSet().isMember(REDIS_ACTIVE_ROOMS_KEY, roomId.toString());
    }

    @Override
    public boolean isCardActive(String cardId) {
        return redis.opsForSet().isMember(REDIS_ACTIVE_CARDS_KEY, cardId);
    }

    @Override
    public UUID getCardUserMapping(String cardId) {
        String key = REDIS_CARD_USER_MAPPING_KEY.formatted(cardId);
        String user = redis.opsForValue().get(key);
        if (user == null) throw new CardWithoutUserException(cardId);
        return UUID.fromString(user);
    }

    @Override
    public UUID getDeviceRoomMapping(UUID deviceId) {
        String key = REDIS_DEVICE_ROOM_MAPPING_KEY.formatted(deviceId);
        String room = redis.opsForValue().get(key);
        if (room == null) throw new DeviceWithoutRoomException(deviceId);
        return UUID.fromString(room);
    }

    @Override
    public UUID getTokenDeviceMapping(String token) {
        String key = REDIS_TOKEN_HASH_DEVICE_MAPPING_KEY.formatted(token);
        String device = redis.opsForValue().get(key);
        if (device == null) throw new TokenNotFoundException();
        return UUID.fromString(device);
    }

    @Override
    public boolean isEntryPermitted(UUID userId, UUID roomId) {
        String roomRolesKey = REDIS_ROOM_ROLES_SET_KEY.formatted(roomId);
        String userRolesKey = REDIS_USER_ROLES_SET_KEY.formatted(userId);

        Set<String> allowedRoles = redis.opsForSet()
                .intersect(userRolesKey, Set.of(roomRolesKey, REDIS_ACTIVE_ROLES_KEY));

        return allowedRoles != null && !allowedRoles.isEmpty();
    }

    @Override
    public void addActiveRole(UUID roleId) {
        redis.opsForSet().add(REDIS_ACTIVE_ROLES_KEY, roleId.toString());
    }

    @Override
    public void addActiveRoles(Collection<UUID> roleIds) {
        addSetMembers(REDIS_ACTIVE_ROLES_KEY, toStringArray(roleIds));
    }

    @Override
    public void removeActiveRole(UUID roleId) {
        redis.opsForSet().remove(REDIS_ACTIVE_ROLES_KEY, roleId.toString());
    }

    @Override
    public void removeRole(UUID roleId) {
        String roleUsersKey = REDIS_ROLE_USERS_SET_KEY.formatted(roleId);
        String roleRoomsKey = REDIS_ROLE_ROOMS_SET_KEY.formatted(roleId);

        Set<String> userIds = redis.opsForSet().members(roleUsersKey);
        if (userIds != null) {
            for (String userId : userIds) {
                redis.opsForSet().remove(REDIS_USER_ROLES_SET_KEY.formatted(userId), roleId.toString());
            }
        }

        Set<String> roomIds = redis.opsForSet().members(roleRoomsKey);
        if (roomIds != null) {
            for (String roomId : roomIds) {
                redis.opsForSet().remove(REDIS_ROOM_ROLES_SET_KEY.formatted(roomId), roleId.toString());
            }
        }

        removeActiveRole(roleId);
        redis.delete(roleUsersKey);
        redis.delete(roleRoomsKey);
    }

    @Override
    public void addActiveCard(String cardId) {
        redis.opsForSet().add(REDIS_ACTIVE_CARDS_KEY, cardId);
    }

    @Override
    public void addActiveCards(Collection<String> cardIds) {
        if (cardIds.isEmpty()) {
            return;
        }
        addSetMembers(REDIS_ACTIVE_CARDS_KEY, cardIds.toArray(String[]::new));
    }

    @Override
    public void removeActiveCard(String cardId) {
        redis.opsForSet().remove(REDIS_ACTIVE_CARDS_KEY, cardId);
    }

    @Override
    public void addActiveRoom(UUID roomId) {
        redis.opsForSet().add(REDIS_ACTIVE_ROOMS_KEY, roomId.toString());
    }

    @Override
    public void addActiveRooms(Collection<UUID> roomIds) {
        addSetMembers(REDIS_ACTIVE_ROOMS_KEY, toStringArray(roomIds));
    }

    @Override
    public void removeActiveRoom(UUID roomId) {
        redis.opsForSet().remove(REDIS_ACTIVE_ROOMS_KEY, roomId.toString());
    }

    @Override
    public void removeRoom(UUID roomId) {
        String roomDevicesKey = REDIS_ROOM_DEVICES_SET_KEY.formatted(roomId);
        String roomRolesKey = REDIS_ROOM_ROLES_SET_KEY.formatted(roomId);

        Set<String> roleIds = redis.opsForSet().members(roomRolesKey);
        if (roleIds != null) {
            for (String roleId : roleIds) {
                redis.opsForSet().remove(REDIS_ROLE_ROOMS_SET_KEY.formatted(roleId), roomId.toString());
            }
        }

        Set<String> deviceIds = redis.opsForSet().members(roomDevicesKey);
        if (deviceIds != null) {
            for (String deviceId : deviceIds) {
                redis.delete(REDIS_DEVICE_ROOM_MAPPING_KEY.formatted(deviceId));
            }
        }

        removeActiveRoom(roomId);
        redis.delete(roomRolesKey);
        redis.delete(roomDevicesKey);
    }

    @Override
    public void addRoleToACL(UUID roleId, UUID roomId) {
        redis.opsForSet().add(REDIS_ROOM_ROLES_SET_KEY.formatted(roomId), roleId.toString());
        redis.opsForSet().add(REDIS_ROLE_ROOMS_SET_KEY.formatted(roleId), roomId.toString());
    }

    @Override
    public void addRolesToRooms(Map<UUID, Set<UUID>> roomRoles) {
        Map<UUID, Set<UUID>> roleRooms = new HashMap<>();

        for (Map.Entry<UUID, Set<UUID>> entry : roomRoles.entrySet()) {
            UUID roomId = entry.getKey();
            Set<UUID> roles = entry.getValue();
            if (roles == null || roles.isEmpty()) {
                continue;
            }

            addSetMembers(REDIS_ROOM_ROLES_SET_KEY.formatted(roomId), toStringArray(roles));
            for (UUID roleId : roles) {
                roleRooms.computeIfAbsent(roleId, ignored -> new HashSet<>()).add(roomId);
            }
        }

        roleRooms.forEach((roleId, rooms) ->
                addSetMembers(REDIS_ROLE_ROOMS_SET_KEY.formatted(roleId), toStringArray(rooms)));
    }

    @Override
    public void removeRoleFromACL(UUID roleId, UUID roomId) {
        redis.opsForSet().remove(REDIS_ROOM_ROLES_SET_KEY.formatted(roomId), roleId.toString());
        redis.opsForSet().remove(REDIS_ROLE_ROOMS_SET_KEY.formatted(roleId), roomId.toString());
    }

    @Override
    public void setDeviceRoom(UUID roomId, UUID deviceId) {
        String deviceKey = REDIS_DEVICE_ROOM_MAPPING_KEY.formatted(deviceId);
        String previousRoomId = redis.opsForValue().get(deviceKey);
        if (previousRoomId != null && !previousRoomId.equals(roomId.toString())) {
            redis.opsForSet().remove(REDIS_ROOM_DEVICES_SET_KEY.formatted(previousRoomId), deviceId.toString());
        }
        redis.opsForValue().set(deviceKey, roomId.toString());
        redis.opsForSet().add(REDIS_ROOM_DEVICES_SET_KEY.formatted(roomId), deviceId.toString());
    }

    @Override
    public void setDeviceRooms(Map<UUID, UUID> deviceRooms) {
        if (deviceRooms.isEmpty()) {
            return;
        }

        Map<String, String> deviceRoomValues = new HashMap<>();
        Map<UUID, Set<UUID>> roomDevices = new HashMap<>();

        for (Map.Entry<UUID, UUID> entry : deviceRooms.entrySet()) {
            UUID deviceId = entry.getKey();
            UUID roomId = entry.getValue();
            deviceRoomValues.put(REDIS_DEVICE_ROOM_MAPPING_KEY.formatted(deviceId), roomId.toString());
            roomDevices.computeIfAbsent(roomId, ignored -> new HashSet<>()).add(deviceId);
        }

        redis.opsForValue().multiSet(deviceRoomValues);
        roomDevices.forEach((roomId, devices) ->
                addSetMembers(REDIS_ROOM_DEVICES_SET_KEY.formatted(roomId), toStringArray(devices)));
    }

    @Override
    public void removeDeviceRoom(UUID deviceId) {
        String deviceKey = REDIS_DEVICE_ROOM_MAPPING_KEY.formatted(deviceId);
        String roomId = redis.opsForValue().getAndDelete(deviceKey);
        if (roomId != null) {
            redis.opsForSet().remove(REDIS_ROOM_DEVICES_SET_KEY.formatted(roomId), deviceId.toString());
        }
    }

    @Override
    public void setCardUser(String cardId, UUID userId) {
        redis.opsForValue().set(REDIS_CARD_USER_MAPPING_KEY.formatted(cardId), userId.toString());
        redis.opsForSet().add(REDIS_USER_CARDS_SET_KEY.formatted(userId), cardId);
    }

    @Override
    public void setCardUsers(Map<String, UUID> cardUsers) {
        if (cardUsers.isEmpty()) {
            return;
        }

        Map<String, String> cardUserValues = new HashMap<>();
        Map<UUID, Set<String>> userCards = new HashMap<>();

        for (Map.Entry<String, UUID> entry : cardUsers.entrySet()) {
            String cardId = entry.getKey();
            UUID userId = entry.getValue();
            cardUserValues.put(REDIS_CARD_USER_MAPPING_KEY.formatted(cardId), userId.toString());
            userCards.computeIfAbsent(userId, ignored -> new HashSet<>()).add(cardId);
        }

        redis.opsForValue().multiSet(cardUserValues);
        userCards.forEach((userId, cards) ->
                addSetMembers(REDIS_USER_CARDS_SET_KEY.formatted(userId), cards.toArray(String[]::new)));
    }

    @Override
    public void removeCardUser(String cardId) {
        String userId = redis.opsForValue().getAndDelete(REDIS_CARD_USER_MAPPING_KEY.formatted(cardId));
        if (userId != null) {
            redis.opsForSet().remove(REDIS_USER_CARDS_SET_KEY.formatted(userId), cardId);
        }
    }

    @Override
    public void addRoleToUser(UUID roleId, UUID userId) {
        redis.opsForSet().add(REDIS_USER_ROLES_SET_KEY.formatted(userId), roleId.toString());
        redis.opsForSet().add(REDIS_ROLE_USERS_SET_KEY.formatted(roleId), userId.toString());
    }

    @Override
    public void addRolesToUsers(Map<UUID, Set<UUID>> userRoles) {
        Map<UUID, Set<UUID>> roleUsers = new HashMap<>();

        for (Map.Entry<UUID, Set<UUID>> entry : userRoles.entrySet()) {
            UUID userId = entry.getKey();
            Set<UUID> roles = entry.getValue();
            if (roles == null || roles.isEmpty()) {
                continue;
            }

            addSetMembers(REDIS_USER_ROLES_SET_KEY.formatted(userId), toStringArray(roles));
            for (UUID roleId : roles) {
                roleUsers.computeIfAbsent(roleId, ignored -> new HashSet<>()).add(userId);
            }
        }

        roleUsers.forEach((roleId, users) ->
                addSetMembers(REDIS_ROLE_USERS_SET_KEY.formatted(roleId), toStringArray(users)));
    }

    @Override
    public void removeRoleFromUser(UUID roleId, UUID userId) {
        redis.opsForSet().remove(REDIS_USER_ROLES_SET_KEY.formatted(userId), roleId.toString());
        redis.opsForSet().remove(REDIS_ROLE_USERS_SET_KEY.formatted(roleId), userId.toString());
    }

    @Override
    public void removeUser(UUID userId) {
        String userRolesKey = REDIS_USER_ROLES_SET_KEY.formatted(userId);
        String userCardsKey = REDIS_USER_CARDS_SET_KEY.formatted(userId);

        Set<String> roleIds = redis.opsForSet().members(userRolesKey);
        if (roleIds != null) {
            for (String roleId : roleIds) {
                redis.opsForSet().remove(REDIS_ROLE_USERS_SET_KEY.formatted(roleId), userId.toString());
            }
        }

        Set<String> cardIds = redis.opsForSet().members(userCardsKey);
        if (cardIds != null) {
            for (String cardId : cardIds) {
                redis.opsForSet().remove(REDIS_ACTIVE_CARDS_KEY, cardId);
                redis.delete(REDIS_CARD_USER_MAPPING_KEY.formatted(cardId));
            }
        }

        redis.delete(userRolesKey);
        redis.delete(userCardsKey);
    }

    @Override
    public void addToken(String tokenHash, UUID deviceId) {
        redis.opsForValue().set(REDIS_TOKEN_HASH_DEVICE_MAPPING_KEY.formatted(tokenHash), deviceId.toString());
        redis.opsForValue().set(REDIS_DEVICE_TOKEN_HASH_MAPPING_KEY.formatted(deviceId), tokenHash);
    }

    @Override
    public void addTokens(Map<UUID, String> deviceTokens) {
        if (deviceTokens.isEmpty()) {
            return;
        }

        Map<String, String> deviceTokenValues = new HashMap<>();
        Map<String, String> tokenDeviceValues = new HashMap<>();

        for (Map.Entry<UUID, String> entry : deviceTokens.entrySet()) {
            UUID deviceId = entry.getKey();
            String tokenHash = entry.getValue();
            deviceTokenValues.put(REDIS_DEVICE_TOKEN_HASH_MAPPING_KEY.formatted(deviceId), tokenHash);
            tokenDeviceValues.put(REDIS_TOKEN_HASH_DEVICE_MAPPING_KEY.formatted(tokenHash), deviceId.toString());
        }

        redis.opsForValue().multiSet(deviceTokenValues);
        redis.opsForValue().multiSet(tokenDeviceValues);
    }

    @Override
    public void removeToken(UUID deviceId) {
        String tokenHash = redis.opsForValue().getAndDelete(REDIS_DEVICE_TOKEN_HASH_MAPPING_KEY.formatted(deviceId));
        if (tokenHash != null) {
            redis.delete(REDIS_TOKEN_HASH_DEVICE_MAPPING_KEY.formatted(tokenHash));
        }
    }

    private void addSetMembers(String key, String[] members) {
        if (members.length == 0) {
            return;
        }
        redis.opsForSet().add(key, members);
    }

    private static String[] toStringArray(Collection<UUID> ids) {
        return ids.stream().map(UUID::toString).toArray(String[]::new);
    }
}
