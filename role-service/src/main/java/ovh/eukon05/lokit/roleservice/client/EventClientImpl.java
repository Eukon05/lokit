package ovh.eukon05.lokit.roleservice.client;

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
    public void sendRoleCreatedEvent(RoleCreatedEventDTO dto) {
        sendObject(ROLE_CREATED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoleDeletedEvent(RoleDeletedEventDTO dto) {
        sendObject(ROLE_DELETED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoleEnabledEvent(RoleEnabledEventDTO dto) {
        sendObject(ROLE_ENABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoleDisabledEvent(RoleDisabledEventDTO dto) {
        sendObject(ROLE_DISABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendUserRoleAddedEvent(UserRoleAddedEventDTO dto) {
        sendObject(USER_ROLE_ASSIGNED_ROUTING_KEY, dto);
    }

    @Override
    public void sendUserRoleRemovedEvent(UserRoleRemovedEventDTO dto) {
        sendObject(USER_ROLE_REMOVED_ROUTING_KEY, dto);
    }

    private void sendObject(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, message);
        } catch (AmqpException exception) {
            log.warn("Failed to publish event {}", message.toString(), exception);
        }
    }
}
