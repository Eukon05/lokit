package ovh.eukon05.lokit.decisionservice.listener;

import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class StartupListener {
    private final RoleServiceClient roleClient;
    private final DeviceServiceClient deviceClient;
    private final RoomServiceClient roomClient;
    private final CardServiceClient cardClient;
    private final StringRedisTemplate redis;

    @EventListener(ApplicationStartedEvent.class)
    public void populateCache() {
        List<DeviceState> devices = deviceClient.listActiveDevices();
        List<RoomState> rooms = roomClient.listActiveRooms();
        List<CardState> cards = cardClient.listActiveCards();
        List<UserState> users = roleClient.listUserRoles();
        String[] roleIds = roleClient.listActiveRoles().stream().map(UUID::toString).toArray(String[]::new);

        redis.opsForSet().add("active.roles", roleIds);
        cards.forEach(card -> redis.opsForSet().add("active.cards", card.id().toString()));
        rooms.forEach(room -> redis.opsForSet().add("active.rooms", room.id().toString()));

        Map<String, String> deviceMap = devices.stream()
                .collect(Collectors.toMap(dev -> "device." + dev.id().toString() + ".room", dev -> dev.roomId().toString()));

        Map<String, String> cardMap = cards.stream()
                .collect(Collectors.toMap(card -> "card." + card.id().toString() + ".user", card -> card.userId().toString()));

        Map<String, Set<String>> userMap = users.stream()
                .collect(Collectors.toMap(u -> "users." + u.id().toString() + ".roles", u -> u.roles().stream().map(UUID::toString).collect(Collectors.toSet())));

        userMap.forEach((k, v) -> redis.opsForSet().add(k, v.toArray(String[]::new)));

        redis.opsForValue().multiSet(deviceMap);
        redis.opsForValue().multiSet(cardMap);
    }
}
