package ovh.eukon05.lokit.identityservice.client;

import ovh.eukon05.lokit.common.event.dto.UserDeletedEventDTO;

public interface EventClient {
    void sendUserDeletedEvent(UserDeletedEventDTO dto);
}
