package ovh.eukon05.lokit.roleservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.RoleDeletedEventDTO;
import ovh.eukon05.lokit.roleservice.config.RabbitConfig;

@Service
@RequiredArgsConstructor
public class EventClientImpl implements EventClient {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendRoleDeletedEvent(RoleDeletedEventDTO dto) {
        rabbitTemplate.convertAndSend(RabbitConfig.ROLES_EXCHANGE_NAME, RabbitConfig.ROLES_ROUTING_KEY, dto);
    }
}
