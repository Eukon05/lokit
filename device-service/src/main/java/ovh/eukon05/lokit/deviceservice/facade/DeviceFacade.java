package ovh.eukon05.lokit.deviceservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.event.dto.*;
import ovh.eukon05.lokit.deviceservice.client.EventClient;
import ovh.eukon05.lokit.deviceservice.client.RoomClient;
import ovh.eukon05.lokit.deviceservice.dto.request.CreateDeviceDTO;
import ovh.eukon05.lokit.deviceservice.dto.response.GetDeviceDTO;
import ovh.eukon05.lokit.deviceservice.exception.DeviceAlreadyExistsException;
import ovh.eukon05.lokit.deviceservice.exception.RoomNotFoundException;
import ovh.eukon05.lokit.deviceservice.helper.DeviceTokenHelper;
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
        if (deviceService.existsByPhysicalAddress(deviceDTO.physicalAddress()))
            throw new DeviceAlreadyExistsException();

        DeviceEntity device = deviceMapper.fromCreateDeviceDTO(deviceDTO);
        UUID id = deviceService.saveDevice(device);
        eventClient.sendDeviceCreatedEvent(new DeviceCreatedEventDTO(Instant.now(), id));
        return id;
    }

    public void deleteDevice(UUID id) {
        deviceService.deleteDevice(id);
        eventClient.sendDeviceDeletedEvent(new DeviceDeletedEventDTO(Instant.now(), id));
    }

    public GetDeviceDTO assignRoom(UUID deviceId, UUID roomId) {
        validateRoom(roomId);
        DeviceEntity device = deviceService.assignRoom(deviceId, roomId);
        eventClient.sendDeviceRoomAssignedEvent(new DeviceRoomAssignedEventDTO(Instant.now(), deviceId, roomId));
        return deviceMapper.toGetDeviceDTO(device);
    }

    public GetDeviceDTO removeRoom(UUID deviceId) {
        DeviceEntity device = deviceService.removeRoom(deviceId);
        eventClient.sendDeviceRoomRemovedEvent(new DeviceRoomRemovedEventDTO(Instant.now(), deviceId));
        return deviceMapper.toGetDeviceDTO(device);
    }

    public String assignToken(UUID id) {
        String token = deviceService.assignToken(id);
        eventClient.sendDeviceTokenAssignedEvent(new DeviceTokenAssignedEventDTO(Instant.now(), id, DeviceTokenHelper.generateHash(token)));
        return token;
    }

    public void revokeToken(UUID id) {
        deviceService.revokeToken(id);
        eventClient.sendDeviceTokenRevokedEvent(new DeviceTokenRevokedEventDTO(Instant.now(), id));
    }

    public PagedModel<GetDeviceDTO> findAll(Pageable pageable) {
        return new PagedModel<>(deviceService.findAll(pageable).map(deviceMapper::toGetDeviceDTO));
    }

    private void validateRoom(UUID roomId) {
        if (!roomClient.checkRoomExists(roomId)) throw new RoomNotFoundException();
    }
}
