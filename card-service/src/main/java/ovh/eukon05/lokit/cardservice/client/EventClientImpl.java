package ovh.eukon05.lokit.cardservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.CardCreatedEventDTO;
import ovh.eukon05.lokit.common.dto.event.CardDeletedEventDTO;
import ovh.eukon05.lokit.common.dto.event.CardDisabledEventDTO;
import ovh.eukon05.lokit.common.dto.event.CardEnabledEventDTO;

import static ovh.eukon05.lokit.common.dto.config.RabbitConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventClientImpl implements EventClient {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendCardCreatedEvent(CardCreatedEventDTO dto) {
        sendObject(CARD_CREATED_ROUTING_KEY, dto);
    }

    @Override
    public void sendCardDeletedEvent(CardDeletedEventDTO dto) {
        sendObject(CARD_DELETED_ROUTING_KEY, dto);
    }

    @Override
    public void sendCardEnabledEvent(CardEnabledEventDTO dto) {
        sendObject(CARD_ENABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendCardDisabledEvent(CardDisabledEventDTO dto) {
        sendObject(CARD_DISABLED_ROUTING_KEY, dto);
    }

    private void sendObject(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, message);
        } catch (AmqpException exception) {
            log.warn("Failed to publish event {}", message.toString(), exception);
        }
    }
}
