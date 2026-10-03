package ovh.eukon05.lokit.deviceservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ovh.eukon05.lokit.deviceservice.model.DeviceCommandEntity;
import ovh.eukon05.lokit.deviceservice.model.DeviceCommandStatus;

import java.util.UUID;

public interface DeviceCommandService {
    DeviceCommandEntity findById(UUID commandId);

    UUID saveCommand(DeviceCommandEntity command);

    Page<DeviceCommandEntity> findAllByDeviceId(UUID deviceId, Pageable pageable);

    void updateCommandStatus(UUID commandId, DeviceCommandStatus status);
}
