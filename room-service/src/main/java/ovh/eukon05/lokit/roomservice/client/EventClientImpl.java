package ovh.eukon05.lokit.roomservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.*;

import static ovh.eukon05.lokit.common.dto.config.RabbitConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventClientImpl implements EventClient {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendRoomCreatedEvent(RoomCreatedEventDTO dto) {
        sendObject(ROOM_CREATED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoleAddedToACLEvent(RoomRoleAddedEventDTO dto) {
        sendObject(ROOM_ROLE_ASSIGNED, dto);
    }

    @Override
    public void sendRoleRemovedFromACLEvent(RoomRoleRemovedEventDTO dto) {
        sendObject(ROOM_ROLE_REMOVED, dto);
    }

    @Override
    public void sendRoomEnabledEvent(RoomEnabledEventDTO dto) {
        sendObject(ROOM_ENABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoomDisabledEvent(RoomDisabledEventDTO dto) {
        sendObject(ROOM_DISABLED_ROUTING_KEY, dto);
    }

    private void sendObject(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, message);
        } catch (AmqpException exception) {
            log.warn("Failed to publish event {}", message.toString(), exception);
        }
    }
}
