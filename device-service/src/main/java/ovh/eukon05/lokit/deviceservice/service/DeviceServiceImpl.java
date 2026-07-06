package ovh.eukon05.lokit.deviceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.deviceservice.exception.DeviceNotFoundException;
import ovh.eukon05.lokit.deviceservice.model.DeviceEntity;
import ovh.eukon05.lokit.deviceservice.repository.DeviceRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    @Override
    public DeviceEntity findById(UUID deviceId) {
        return deviceRepository.findById(deviceId).orElseThrow(DeviceNotFoundException::new);
    }

    @Override
    public UUID saveDevice(DeviceEntity device) {
        deviceRepository.save(device);
        return device.getId();
    }

    @Override
    public void deleteDevice(UUID deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    @Override
    public DeviceEntity assignRoom(UUID deviceId, UUID roomId) {
        DeviceEntity device = findById(deviceId);
        device.setRoomId(roomId);
        return deviceRepository.save(device);
    }

    @Override
    public DeviceEntity removeRoom(UUID deviceId) {
        DeviceEntity device = findById(deviceId);
        device.setRoomId(null);
        return deviceRepository.save(device);
    }

    @Override
    public DeviceEntity enableDevice(UUID deviceId) {
        DeviceEntity device = findById(deviceId);
        device.setActive(true);
        return deviceRepository.save(device);
    }

    @Override
    public DeviceEntity disableDevice(UUID deviceId) {
        DeviceEntity device = findById(deviceId);
        device.setActive(false);
        return deviceRepository.save(device);
    }

    @Override
    public Page<DeviceEntity> findAll(Pageable pageable) {
        return deviceRepository.findAll(pageable);
    }
}
