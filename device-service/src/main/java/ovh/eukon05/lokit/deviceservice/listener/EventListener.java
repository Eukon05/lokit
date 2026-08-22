package ovh.eukon05.lokit.deviceservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ovh.eukon05.lokit.common.event.dto.RoomDeletedEventDTO;
import ovh.eukon05.lokit.deviceservice.service.DeviceService;

import static ovh.eukon05.lokit.common.config.RabbitConstants.DEVICE_SERVICE_QUEUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventListener {
    private final DeviceService deviceService;

    @RabbitListener(queues = DEVICE_SERVICE_QUEUE)
    void receiveRoomDeletedEvent(RoomDeletedEventDTO dto) {
        log.debug("Received room deleted event. Deleting device-room mappings for room {}", dto.roomId());
        deviceService.removeRoomFromAll(dto.roomId());
    }
}
