package ovh.eukon05.lokit.deviceservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.event.RabbitEventPublisher;
import ovh.eukon05.lokit.common.event.dto.*;

import static ovh.eukon05.lokit.common.config.RabbitConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventClientImpl implements EventClient {
    private final RabbitEventPublisher publisher;

    @Override
    public void sendDeviceCreatedEvent(DeviceCreatedEventDTO dto) {
        publisher.sendObject(DEVICE_CREATED_ROUTING_KEY, dto);
    }

    @Override
    public void sendDeviceDeletedEvent(DeviceDeletedEventDTO dto) {
        publisher.sendObject(DEVICE_DELETED_ROUTING_KEY, dto);
    }

    @Override
    public void sendDeviceTokenAssignedEvent(DeviceTokenAssignedEventDTO dto) {
        publisher.sendObject(DEVICE_TOKEN_ASSIGNED_ROUTING_KEY, dto);
    }

    @Override
    public void sendDeviceTokenRevokedEvent(DeviceTokenRevokedEventDTO dto) {
        publisher.sendObject(DEVICE_TOKEN_REVOKED_ROUTING_KEY, dto);
    }

    @Override
    public void sendDeviceRoomAssignedEvent(DeviceRoomAssignedEventDTO dto) {
        publisher.sendObject(DEVICE_ROOM_ASSIGNED_ROUTING_KEY, dto);
    }

    @Override
    public void sendDeviceRoomRemovedEvent(DeviceRoomRemovedEventDTO dto) {
        publisher.sendObject(DEVICE_ROOM_REMOVED_ROUTING_KEY, dto);
    }
}
