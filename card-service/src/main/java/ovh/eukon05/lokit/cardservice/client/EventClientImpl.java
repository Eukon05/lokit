package ovh.eukon05.lokit.cardservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.RabbitEventPublisher;
import ovh.eukon05.lokit.common.dto.event.dto.CardCreatedEventDTO;
import ovh.eukon05.lokit.common.dto.event.dto.CardDeletedEventDTO;
import ovh.eukon05.lokit.common.dto.event.dto.CardDisabledEventDTO;
import ovh.eukon05.lokit.common.dto.event.dto.CardEnabledEventDTO;

import static ovh.eukon05.lokit.common.dto.config.RabbitConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventClientImpl implements EventClient {
    private final RabbitEventPublisher publisher;

    @Override
    public void sendCardCreatedEvent(CardCreatedEventDTO dto) {
        publisher.sendObject(CARD_CREATED_ROUTING_KEY, dto);
    }

    @Override
    public void sendCardDeletedEvent(CardDeletedEventDTO dto) {
        publisher.sendObject(CARD_DELETED_ROUTING_KEY, dto);
    }

    @Override
    public void sendCardEnabledEvent(CardEnabledEventDTO dto) {
        publisher.sendObject(CARD_ENABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendCardDisabledEvent(CardDisabledEventDTO dto) {
        publisher.sendObject(CARD_DISABLED_ROUTING_KEY, dto);
    }
}
