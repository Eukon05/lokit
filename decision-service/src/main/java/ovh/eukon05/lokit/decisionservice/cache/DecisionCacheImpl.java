package ovh.eukon05.lokit.decisionservice.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import ovh.eukon05.lokit.decisionservice.exception.CardWithoutUserException;
import ovh.eukon05.lokit.decisionservice.exception.DeviceWithoutRoomException;
import ovh.eukon05.lokit.decisionservice.exception.TokenNotFoundException;

import java.util.Set;
import java.util.UUID;

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
    public boolean isCardActive(UUID cardId) {
        return redis.opsForSet().isMember(REDIS_ACTIVE_CARDS_KEY, cardId.toString());
    }

    @Override
    public UUID getCardUserMapping(UUID cardId) {
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
    public void removeActiveRole(UUID roleId) {
        redis.opsForSet().remove(REDIS_ACTIVE_ROLES_KEY, roleId.toString());
    }

    @Override
    public void addActiveCard(UUID cardId) {
        redis.opsForSet().add(REDIS_ACTIVE_CARDS_KEY, cardId.toString());
    }

    @Override
    public void removeActiveCard(UUID cardId) {
        redis.opsForSet().remove(REDIS_ACTIVE_CARDS_KEY, cardId.toString());
    }

    @Override
    public void addActiveRoom(UUID roomId) {
        redis.opsForSet().add(REDIS_ACTIVE_ROOMS_KEY, roomId.toString());
    }

    @Override
    public void removeActiveRoom(UUID roomId) {
        redis.opsForSet().remove(REDIS_ACTIVE_ROOMS_KEY, roomId.toString());
    }

    @Override
    public void addRoleToACL(UUID roleId, UUID roomId) {
        redis.opsForSet().add(REDIS_ROOM_ROLES_SET_KEY.formatted(roomId), roleId.toString());
    }

    @Override
    public void removeRoleFromACL(UUID roleId, UUID roomId) {
        redis.opsForSet().remove(REDIS_ROOM_ROLES_SET_KEY.formatted(roomId), roleId.toString());
    }

    @Override
    public void setDeviceRoom(UUID roomId, UUID deviceId) {
        redis.opsForValue().set(REDIS_DEVICE_ROOM_MAPPING_KEY.formatted(deviceId), roomId.toString());
    }

    @Override
    public void removeDeviceRoom(UUID deviceId) {
        redis.opsForValue().getAndDelete(REDIS_DEVICE_ROOM_MAPPING_KEY.formatted(deviceId));
    }

    @Override
    public void setCardUser(UUID cardId, UUID userId) {
        redis.opsForValue().set(REDIS_CARD_USER_MAPPING_KEY.formatted(cardId), userId.toString());
    }

    @Override
    public void removeCardUser(UUID cardId) {
        redis.opsForValue().getAndDelete(REDIS_CARD_USER_MAPPING_KEY.formatted(cardId));
    }

    @Override
    public void addRoleToUser(UUID roleId, UUID userId) {
        redis.opsForSet().add(REDIS_USER_ROLES_SET_KEY.formatted(userId), roleId.toString());
    }

    @Override
    public void removeRoleFromUser(UUID roleId, UUID userId) {
        redis.opsForSet().remove(REDIS_USER_ROLES_SET_KEY.formatted(userId), roleId.toString());
    }

    @Override
    public void addToken(String tokenHash, UUID deviceId) {
        redis.opsForValue().set(REDIS_TOKEN_HASH_DEVICE_MAPPING_KEY.formatted(tokenHash), deviceId.toString());
        redis.opsForValue().set(REDIS_DEVICE_TOKEN_HASH_MAPPING_KEY.formatted(deviceId), tokenHash);
    }

    @Override
    public void removeToken(UUID deviceId) {
        String tokenHash = redis.opsForValue().getAndDelete(REDIS_DEVICE_TOKEN_HASH_MAPPING_KEY.formatted(deviceId));
        if (tokenHash != null) {
            redis.delete(REDIS_TOKEN_HASH_DEVICE_MAPPING_KEY.formatted(tokenHash));
        }
    }
}
