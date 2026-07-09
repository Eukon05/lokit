package ovh.eukon05.lokit.deviceservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.dto.*;
import ovh.eukon05.lokit.deviceservice.client.EventClient;
import ovh.eukon05.lokit.deviceservice.client.RoomClient;
import ovh.eukon05.lokit.deviceservice.dto.request.CreateDeviceDTO;
import ovh.eukon05.lokit.deviceservice.dto.response.GetDeviceDTO;
import ovh.eukon05.lokit.deviceservice.exception.RoomNotFoundException;
import ovh.eukon05.lokit.deviceservice.mapper.DeviceMapper;
import ovh.eukon05.lokit.deviceservice.model.DeviceEntity;
import ovh.eukon05.lokit.deviceservice.service.DeviceService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceFacade {
    private final DeviceService deviceService;
    private final RoomClient roomClient;
    private final EventClient eventClient;
    private final DeviceMapper deviceMapper;

    public GetDeviceDTO getDevice(UUID id) {
        return deviceMapper.toGetDeviceDTO(deviceService.findById(id));
    }

    public UUID createDevice(CreateDeviceDTO deviceDTO) {
        validateRoom(deviceDTO.roomId());
        DeviceEntity device = deviceMapper.fromCreateDeviceDTO(deviceDTO);
        UUID id = deviceService.saveDevice(device);
        eventClient.sendDeviceCreatedEvent(new DeviceCreatedEventDTO(Instant.now(), id, deviceDTO.roomId()));
        return id;
    }

    public void deleteDevice(UUID id) {
        deviceService.deleteDevice(id);
        eventClient.sendDeviceDeletedEvent(new DeviceDeletedEventDTO(Instant.now(), id));
    }

    public GetDeviceDTO assignRoom(UUID deviceId, UUID roomId) {
        validateRoom(roomId);
        DeviceEntity device = deviceService.assignRoom(deviceId, roomId);
        eventClient.sendDeviceRoomAssignedEvent(new DeviceRoomAssignedEventDTO(Instant.now(), deviceId, roomId, device.isActive()));
        return deviceMapper.toGetDeviceDTO(device);
    }

    public GetDeviceDTO removeRoom(UUID deviceId) {
        DeviceEntity device = deviceService.removeRoom(deviceId);
        eventClient.sendDeviceRoomRemovedEvent(new DeviceRoomRemovedEventDTO(Instant.now(), deviceId));
        return deviceMapper.toGetDeviceDTO(device);
    }

    public void enableDevice(UUID id) {
        DeviceEntity device = deviceService.enableDevice(id);
        if (device.getRoomId() != null)
            eventClient.sendDeviceEnabledEvent(new DeviceEnabledEventDTO(Instant.now(), id, device.getRoomId()));
    }

    public void disableDevice(UUID id) {
        deviceService.disableDevice(id);
        eventClient.sendDeviceDisabledEvent(new DeviceDisabledEventDTO(Instant.now(), id));
    }

    public PagedModel<GetDeviceDTO> findAll(Pageable pageable) {
        return new PagedModel<>(deviceService.findAll(pageable).map(deviceMapper::toGetDeviceDTO));
    }

    private void validateRoom(UUID roomId) {
        if (!roomClient.checkRoomExists(roomId)) throw new RoomNotFoundException();
    }
}
