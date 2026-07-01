package ovh.eukon05.lokit.roomservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.RoleDeletedEventDTO;
import ovh.eukon05.lokit.common.dto.event.RoomDisabledEventDTO;
import ovh.eukon05.lokit.common.dto.event.RoomEnabledEventDTO;
import ovh.eukon05.lokit.roomservice.config.RabbitConfig;
import ovh.eukon05.lokit.roomservice.service.RoomService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventClientImpl implements EventClient {
    private final RoomService roomService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @RabbitListener(queues = RabbitConfig.ROOM_SERVICE_QUEUE)
    public void receiveRoleDeletedEvent(RoleDeletedEventDTO event) {
        roomService.removeFromAllACLs(event.roleId());
    }

    @Override
    public void sendRoomEnabledEvent(RoomEnabledEventDTO dto) {
        sendObject(RabbitConfig.ROOM_ENABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoomDisabledEvent(RoomDisabledEventDTO dto) {
        sendObject(RabbitConfig.ROOM_DISABLED_ROUTING_KEY, dto);
    }

    private void sendObject(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.ROLES_EXCHANGE_NAME, routingKey, message);
        } catch (AmqpException exception) {
            log.warn("Failed to publish event {}", message.toString(), exception);
        }
    }
}
