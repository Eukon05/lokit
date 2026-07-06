package ovh.eukon05.lokit.deviceservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ovh.eukon05.lokit.deviceservice.model.DeviceEntity;

import java.util.UUID;

public interface DeviceService {
    DeviceEntity findById(UUID deviceId);

    UUID saveDevice(DeviceEntity device);

    void deleteDevice(UUID deviceId);

    DeviceEntity assignRoom(UUID deviceId, UUID roomId);

    DeviceEntity removeRoom(UUID deviceId);

    DeviceEntity enableDevice(UUID deviceId);

    DeviceEntity disableDevice(UUID deviceId);

    Page<DeviceEntity> findAll(Pageable pageable);
}
