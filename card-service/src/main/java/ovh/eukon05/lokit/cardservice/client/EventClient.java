package ovh.eukon05.lokit.cardservice.client;

import ovh.eukon05.lokit.common.dto.event.CardCreatedEventDTO;
import ovh.eukon05.lokit.common.dto.event.CardDeletedEventDTO;
import ovh.eukon05.lokit.common.dto.event.CardDisabledEventDTO;
import ovh.eukon05.lokit.common.dto.event.CardEnabledEventDTO;

public interface EventClient {
    void sendCardCreatedEvent(CardCreatedEventDTO dto);

    void sendCardDeletedEvent(CardDeletedEventDTO dto);

    void sendCardEnabledEvent(CardEnabledEventDTO dto);

    void sendCardDisabledEvent(CardDisabledEventDTO dto);
}
