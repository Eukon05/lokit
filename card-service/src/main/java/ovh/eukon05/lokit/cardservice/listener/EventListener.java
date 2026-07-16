package ovh.eukon05.lokit.cardservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ovh.eukon05.lokit.cardservice.service.UserService;
import ovh.eukon05.lokit.common.event.dto.UserDeletedEventDTO;

import static ovh.eukon05.lokit.common.config.RabbitConstants.CARD_SERVICE_QUEUE;

@Component
@RequiredArgsConstructor
@Slf4j
class EventListener {
    private final UserService userService;

    @RabbitListener(queues = CARD_SERVICE_QUEUE)
    void receiveUserDeletedEvent(UserDeletedEventDTO dto) {
        log.debug("Received user deleted event. Deleting local card-service user {} and their cards", dto.userId());
        userService.deleteUser(dto.userId());
    }
}
