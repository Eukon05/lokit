package ovh.eukon05.lokit.roleservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.RoleDeletedEventDTO;
import ovh.eukon05.lokit.common.dto.event.RoleDisabledEventDTO;
import ovh.eukon05.lokit.common.dto.event.RoleEnabledEventDTO;
import ovh.eukon05.lokit.roleservice.config.RabbitConfig;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventClientImpl implements EventClient {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendRoleDeletedEvent(RoleDeletedEventDTO dto) {
        sendObject(RabbitConfig.ROLE_DELETED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoleEnabledEvent(RoleEnabledEventDTO dto) {
        sendObject(RabbitConfig.ROLE_ENABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoleDisabledEvent(RoleDisabledEventDTO dto) {
        sendObject(RabbitConfig.ROLE_DISABLED_ROUTING_KEY, dto);
    }

    private void sendObject(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.ROLES_EXCHANGE_NAME, routingKey, message);
        } catch (AmqpException exception) {
            log.warn("Failed to publish event {}", message.toString(), exception);
        }
    }
}
