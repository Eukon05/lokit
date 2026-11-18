package ovh.eukon05.lokit.decisionservice.listener;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ovh.eukon05.lokit.decisionservice.cache.DecisionCache;
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
@Slf4j
public class CacheWarmupListener {
    private final RoleServiceClient roleClient;
    private final DeviceServiceClient deviceClient;
    private final RoomServiceClient roomClient;
    private final CardServiceClient cardClient;
    private final DecisionCache decisionCache;

    @EventListener(ApplicationStartedEvent.class)
    public void populateCache() throws InterruptedException {
        boolean isPopulated = false;
        int timeout = 15;

        while (!isPopulated) {
            try {
                saveGrpcDataToCache();
                isPopulated = true;
            } catch (StatusRuntimeException e) {
                if (e.getStatus().getCode() == Status.Code.UNAVAILABLE) {
                    log.warn("gRPC endpoint unavailable, waiting for {} seconds...", timeout);
                    Thread.sleep(timeout * 1000L);
                    if (timeout < 480) timeout *= 2;
                }
            }
        }

    }

    private void saveGrpcDataToCache() {
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
        List<UUID> roleIds = roleClient.listActiveRoles();

        log.debug("Saving active roles...");
        decisionCache.addActiveRoles(roleIds);

        log.debug("Saving active cards...");
        decisionCache.addActiveCards(cards.stream().map(CardState::id).toList());

        log.debug("Saving active rooms...");
        decisionCache.addActiveRooms(rooms.stream().map(RoomState::id).toList());

        Map<UUID, UUID> deviceRooms = devices.stream()
                .filter(dev -> dev.roomId() != null)
                .collect(Collectors.toMap(DeviceState::id, DeviceState::roomId));

        Map<UUID, String> deviceTokens = devices.stream()
                .filter(dev -> dev.tokenHash() != null && !dev.tokenHash().isBlank())
                .collect(Collectors.toMap(DeviceState::id, DeviceState::tokenHash));

        Map<String, UUID> cardUsers = cards.stream()
                .collect(Collectors.toMap(CardState::id, CardState::userId));

        Map<UUID, Set<UUID>> userRoles = users.stream()
                .collect(Collectors.toMap(UserState::id, UserState::roles));

        Map<UUID, Set<UUID>> roomRoles = rooms.stream()
                .collect(Collectors.toMap(RoomState::id, RoomState::roles));

        log.debug("Saving device-room mappings...");
        decisionCache.setDeviceRooms(deviceRooms);

        log.debug("Saving device-token mappings...");
        decisionCache.addTokens(deviceTokens);

        log.debug("Saving card-user mappings...");
        decisionCache.setCardUsers(cardUsers);

        log.debug("Saving user-role mappings...");
        decisionCache.addRolesToUsers(userRoles);

        log.debug("Saving room-role mappings...");
        decisionCache.addRolesToRooms(roomRoles);

        log.info("Cache warmup complete!");
    }
}
