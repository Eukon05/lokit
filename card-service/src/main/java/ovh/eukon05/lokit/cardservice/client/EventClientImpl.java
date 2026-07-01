package ovh.eukon05.lokit.cardservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.cardservice.config.RabbitConfig;
import ovh.eukon05.lokit.common.dto.event.CardCreatedEventDTO;
import ovh.eukon05.lokit.common.dto.event.CardDeletedEventDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventClientImpl implements EventClient {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendCardCreatedEvent(CardCreatedEventDTO dto) {
        sendObject(RabbitConfig.CARD_CREATED_ROUTING_KEY, dto);
    }

    @Override
    public void sendCardDeletedEvent(CardDeletedEventDTO dto) {
        sendObject(RabbitConfig.CARD_DELETED_ROUTING_KEY, dto);
    }

    private void sendObject(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.CARD_EXCHANGE_NAME, routingKey, message);
        } catch (AmqpException exception) {
            log.warn("Failed to publish event {}", message.toString(), exception);
        }
    }
}
