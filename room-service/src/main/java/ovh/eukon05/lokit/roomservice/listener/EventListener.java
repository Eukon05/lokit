package ovh.eukon05.lokit.roomservice.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ovh.eukon05.lokit.common.dto.event.RoleDeletedEventDTO;
import ovh.eukon05.lokit.roomservice.service.RoomService;

import static ovh.eukon05.lokit.common.dto.config.RabbitConstants.ROOM_SERVICE_QUEUE;

@Component
@RequiredArgsConstructor
public class EventListener {
    private final RoomService roomService;

    @RabbitListener(queues = ROOM_SERVICE_QUEUE)
    public void receiveRoleDeletedEvent(RoleDeletedEventDTO event) {
        roomService.removeFromAllACLs(event.roleId());
    }
}
