package ovh.eukon05.lokit.cardservice.client;

import ovh.eukon05.lokit.common.dto.event.CardCreatedEventDTO;
import ovh.eukon05.lokit.common.dto.event.CardDeletedEventDTO;

public interface EventClient {
    void sendCardCreatedEvent(CardCreatedEventDTO dto);

    void sendCardDeletedEvent(CardDeletedEventDTO dto);
}
