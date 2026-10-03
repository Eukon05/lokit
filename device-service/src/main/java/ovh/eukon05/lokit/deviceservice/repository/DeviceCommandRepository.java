package ovh.eukon05.lokit.deviceservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ovh.eukon05.lokit.deviceservice.model.DeviceCommandEntity;

import java.util.UUID;

public interface DeviceCommandRepository extends JpaRepository<DeviceCommandEntity, UUID> {
    Page<DeviceCommandEntity> findAllByDevice_Id(UUID deviceId, Pageable pageable);
}
