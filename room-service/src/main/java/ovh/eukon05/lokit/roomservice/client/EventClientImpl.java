package ovh.eukon05.lokit.roomservice.client;

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
    public void sendRoomCreatedEvent(RoomCreatedEventDTO dto) {
        publisher.sendObject(ROOM_CREATED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoleAddedToACLEvent(RoomRoleAddedEventDTO dto) {
        publisher.sendObject(ROOM_ROLE_ASSIGNED, dto);
    }

    @Override
    public void sendRoleRemovedFromACLEvent(RoomRoleRemovedEventDTO dto) {
        publisher.sendObject(ROOM_ROLE_REMOVED, dto);
    }

    @Override
    public void sendRoomEnabledEvent(RoomEnabledEventDTO dto) {
        publisher.sendObject(ROOM_ENABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoomDisabledEvent(RoomDisabledEventDTO dto) {
        publisher.sendObject(ROOM_DISABLED_ROUTING_KEY, dto);
    }
}
