package ovh.eukon05.lokit.roleservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.RoleDeletedEventDTO;
import ovh.eukon05.lokit.roleservice.config.RabbitConfig;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventClientImpl implements EventClient {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendRoleDeletedEvent(RoleDeletedEventDTO dto) {
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.ROLES_EXCHANGE_NAME, RabbitConfig.ROLES_ROUTING_KEY, dto);
        } catch (AmqpException exception) {
            log.warn("Failed to publish role deleted event for role {}", dto.roleId(), exception);
        }
    }
}
