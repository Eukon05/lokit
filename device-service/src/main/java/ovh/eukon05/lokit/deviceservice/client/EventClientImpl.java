package ovh.eukon05.lokit.deviceservice.client;

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
    public void sendDeviceCreatedEvent(DeviceCreatedEventDTO dto) {
        sendObject(DEVICE_CREATED_ROUTING_KEY, dto);
    }

    @Override
    public void sendDeviceDeletedEvent(DeviceDeletedEventDTO dto) {
        sendObject(DEVICE_DELETED_ROUTING_KEY, dto);
    }

    @Override
    public void sendDeviceEnabledEvent(DeviceEnabledEventDTO dto) {
        sendObject(DEVICE_ENABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendDeviceDisabledEvent(DeviceDisabledEventDTO dto) {
        sendObject(DEVICE_DISABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendDeviceRoomAssignedEvent(DeviceRoomAssignedEventDTO dto) {
        sendObject(DEVICE_ROOM_ASSIGNED_ROUTING_KEY, dto);
    }

    @Override
    public void sendDeviceRoomRemovedEvent(DeviceRoomRemovedEventDTO dto) {
        sendObject(DEVICE_ROOM_REMOVED_ROUTING_KEY, dto);
    }

    private void sendObject(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, message);
        } catch (AmqpException exception) {
            log.warn("Failed to publish event {}", message.toString(), exception);
        }
    }
}
