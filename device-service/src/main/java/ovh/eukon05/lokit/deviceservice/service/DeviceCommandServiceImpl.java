package ovh.eukon05.lokit.deviceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.deviceservice.exception.DeviceCommandNotFoundException;
import ovh.eukon05.lokit.deviceservice.model.DeviceCommandEntity;
import ovh.eukon05.lokit.deviceservice.model.DeviceCommandStatus;
import ovh.eukon05.lokit.deviceservice.repository.DeviceCommandRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceCommandServiceImpl implements DeviceCommandService {
    private final DeviceCommandRepository repository;

    @Override
    public DeviceCommandEntity findById(UUID commandId) {
        return repository.findById(commandId).orElseThrow(DeviceCommandNotFoundException::new);
    }

    @Override
    public UUID saveCommand(DeviceCommandEntity command) {
        return repository.save(command).getId();
    }

    @Override
    public Page<DeviceCommandEntity> findAllByDeviceId(UUID deviceId, Pageable pageable) {
        return repository.findAllByDevice_IdOrderByIssuedAtDesc(deviceId, pageable);
    }

    @Override
    public void updateCommandStatus(UUID commandId, DeviceCommandStatus status) {
        DeviceCommandEntity command = findById(commandId);
        command.setStatus(status);
        repository.save(command);
    }
}
