package ovh.eukon05.lokit.deviceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.eukon05.lokit.deviceservice.model.DeviceEntity;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {
    List<DeviceEntity> findAllByTokenHashIsNotNull();

    boolean existsByPhysicalAddress(String physicalAddress);
}
