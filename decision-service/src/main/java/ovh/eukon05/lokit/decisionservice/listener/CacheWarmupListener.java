package ovh.eukon05.lokit.decisionservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import ovh.eukon05.lokit.decisionservice.client.CardServiceClient;
import ovh.eukon05.lokit.decisionservice.client.DeviceServiceClient;
import ovh.eukon05.lokit.decisionservice.client.RoleServiceClient;
import ovh.eukon05.lokit.decisionservice.client.RoomServiceClient;
import ovh.eukon05.lokit.decisionservice.model.CardState;
import ovh.eukon05.lokit.decisionservice.model.DeviceState;
import ovh.eukon05.lokit.decisionservice.model.RoomState;
import ovh.eukon05.lokit.decisionservice.model.UserState;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static ovh.eukon05.lokit.decisionservice.cache.RedisCacheKeys.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheWarmupListener {
    private final RoleServiceClient roleClient;
    private final DeviceServiceClient deviceClient;
    private final RoomServiceClient roomClient;
    private final CardServiceClient cardClient;
    private final StringRedisTemplate redis;

    @EventListener(ApplicationStartedEvent.class)
    public void populateCache() {
        log.info("Starting cache warmup!");
        log.debug("Fetching devices through gRPC...");
        List<DeviceState> devices = deviceClient.listActiveDevices();

        log.debug("Fetching rooms through gRPC...");
        List<RoomState> rooms = roomClient.listActiveRooms();

        log.debug("Fetching cards through gRPC...");
        List<CardState> cards = cardClient.listActiveCards();

        log.debug("Fetching users through gRPC...");
        List<UserState> users = roleClient.listUserRoles();

        log.debug("Fetching roles through gRPC...");
        String[] roleIds = roleClient.listActiveRoles().stream().map(UUID::toString).toArray(String[]::new);

        log.debug("Saving active roles to Redis...");
        addSetMembers(REDIS_ACTIVE_ROLES_KEY, roleIds);

        log.debug("Saving active cards to Redis...");
        cards.forEach(card -> redis.opsForSet().add(REDIS_ACTIVE_CARDS_KEY, card.id()));

        log.debug("Saving active rooms to Redis...");
        rooms.forEach(room -> redis.opsForSet().add(REDIS_ACTIVE_ROOMS_KEY, room.id().toString()));

        Map<String, String> deviceMap = devices.stream()
                .filter(dev -> dev.roomId() != null)
                .collect(Collectors.toMap(dev -> REDIS_DEVICE_ROOM_MAPPING_KEY.formatted(dev.id()), dev -> dev.roomId().toString()));

        Map<String, String> deviceTokenMap = devices.stream()
                .filter(dev -> dev.tokenHash() != null && !dev.tokenHash().isBlank())
                .collect(Collectors.toMap(dev -> REDIS_DEVICE_TOKEN_HASH_MAPPING_KEY.formatted(dev.id()), DeviceState::tokenHash));

        Map<String, String> tokenDeviceMap = devices.stream()
                .filter(dev -> dev.tokenHash() != null && !dev.tokenHash().isBlank())
                .collect(Collectors.toMap(dev -> REDIS_TOKEN_HASH_DEVICE_MAPPING_KEY.formatted(dev.tokenHash()), dev -> dev.id().toString()));

        Map<String, String> cardMap = cards.stream()
                .collect(Collectors.toMap(card -> REDIS_CARD_USER_MAPPING_KEY.formatted(card.id()), card -> card.userId().toString()));

        Map<String, Set<String>> userCardMap = cards.stream()
                .collect(Collectors.groupingBy(
                        card -> REDIS_USER_CARDS_SET_KEY.formatted(card.userId()),
                        Collectors.mapping(CardState::id, Collectors.toSet())
                ));

        Map<String, Set<String>> userMap = users.stream()
                .collect(Collectors.toMap(u -> REDIS_USER_ROLES_SET_KEY.formatted(u.id()), u -> u.roles().stream().map(UUID::toString).collect(Collectors.toSet())));

        Map<String, Set<String>> roomMap = rooms.stream()
                .collect(Collectors.toMap(room -> REDIS_ROOM_ROLES_SET_KEY.formatted(room.id()), room -> room.roles().stream().map(UUID::toString).collect(Collectors.toSet())));

        log.debug("Saving user-role mappings to Redis...");
        userMap.forEach((k, v) -> addSetMembers(k, v.toArray(String[]::new)));

        log.debug("Saving user-card mappings to Redis...");
        userCardMap.forEach((k, v) -> addSetMembers(k, v.toArray(String[]::new)));

        log.debug("Saving room-mappings to Redis...");
        roomMap.forEach((k, v) -> addSetMembers(k, v.toArray(String[]::new)));

        log.debug("Saving device-room mappings to Redis...");
        if (!deviceMap.isEmpty()) {
            redis.opsForValue().multiSet(deviceMap);
        }

        log.debug("Saving device-token mappings to Redis...");
        if (!deviceTokenMap.isEmpty()) {
            redis.opsForValue().multiSet(deviceTokenMap);
        }
        if (!tokenDeviceMap.isEmpty()) {
            redis.opsForValue().multiSet(tokenDeviceMap);
        }

        log.debug("Saving card-user mappings to Redis...");
        if (!cardMap.isEmpty()) {
            redis.opsForValue().multiSet(cardMap);
        }

        log.info("Cache warmup complete!");
    }

    private void addSetMembers(String key, String[] members) {
        if (members.length == 0) {
            log.debug("Skipping Redis set {} because there are no members to add", key);
            return;
        }

        redis.opsForSet().add(key, members);
    }
}
