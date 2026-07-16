package ovh.eukon05.lokit.identityservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.event.RabbitEventPublisher;
import ovh.eukon05.lokit.common.event.dto.UserDeletedEventDTO;

import static ovh.eukon05.lokit.common.config.RabbitConstants.USER_DELETED_ROUTING_KEY;

@Service
@RequiredArgsConstructor
class EventClientImpl implements EventClient {
    private final RabbitEventPublisher publisher;

    @Override
    public void sendUserDeletedEvent(UserDeletedEventDTO dto) {
        publisher.sendObject(USER_DELETED_ROUTING_KEY, dto);
    }
}
