package ovh.eukon05.lokit.cardservice.client;

import ovh.eukon05.lokit.common.event.dto.CardCreatedEventDTO;
import ovh.eukon05.lokit.common.event.dto.CardDeletedEventDTO;
import ovh.eukon05.lokit.common.event.dto.CardDisabledEventDTO;
import ovh.eukon05.lokit.common.event.dto.CardEnabledEventDTO;

public interface EventClient {
    void sendCardCreatedEvent(CardCreatedEventDTO dto);

    void sendCardDeletedEvent(CardDeletedEventDTO dto);

    void sendCardEnabledEvent(CardEnabledEventDTO dto);

    void sendCardDisabledEvent(CardDisabledEventDTO dto);
}
