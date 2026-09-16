package ovh.eukon05.lokit.deviceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.deviceservice.exception.DeviceNotFoundException;
import ovh.eukon05.lokit.deviceservice.helper.DeviceTokenHelper;
import ovh.eukon05.lokit.deviceservice.model.DeviceEntity;
import ovh.eukon05.lokit.deviceservice.repository.DeviceRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
    public boolean existsByPhysicalAddress(String physicalAddress) {
        return deviceRepository.existsByPhysicalAddress(physicalAddress);
    }

    @Override
    public UUID saveDevice(DeviceEntity device) {
        deviceRepository.save(device);
        return device.getId();
    }

    @Override
    public void deleteDevice(UUID deviceId) {
        DeviceEntity device = findById(deviceId);
        deviceRepository.delete(device);
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
    public String assignToken(UUID deviceId) {
        DeviceEntity device = findById(deviceId);
        String token = DeviceTokenHelper.generateRandomString(32);
        device.setTokenHash(DeviceTokenHelper.generateHash(token));

        deviceRepository.save(device);
        return token;
    }

    @Override
    public void revokeToken(UUID deviceId) {
        DeviceEntity device = findById(deviceId);
        device.setTokenHash(null);
        deviceRepository.save(device);
    }

    @Override
    public Page<DeviceEntity> findAll(Pageable pageable) {
        return deviceRepository.findAll(pageable);
    }

    @Override
    public void removeRoomFromAll(UUID roomId) {
        List<DeviceEntity> devices = deviceRepository.findAllByRoomId(roomId);
        devices.forEach(device -> device.setRoomId(null));
        deviceRepository.saveAll(devices);
    }

    @Override
    public void updateLastSeen(String physicalAddress, Instant lastSeenAt) {
        Optional<DeviceEntity> device = deviceRepository.findByPhysicalAddress(physicalAddress);
        device.ifPresent(deviceEntity -> {
            deviceEntity.setLastSeenAt(lastSeenAt);
            deviceRepository.save(deviceEntity);
        });
    }
}
