package ovh.eukon05.lokit.roomservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.RoleDeletedEventDTO;
import ovh.eukon05.lokit.roomservice.config.RabbitConfig;
import ovh.eukon05.lokit.roomservice.service.RoomService;

@Service
@RequiredArgsConstructor
public class EventClientImpl implements EventClient {
    private final RoomService roomService;

    @Override
    @RabbitListener(queues = RabbitConfig.ROOM_SERVICE_QUEUE)
    public void receiveRoleDeletedEvent(RoleDeletedEventDTO event) {
        roomService.removeFromAllACLs(event.roleId());
    }

}
