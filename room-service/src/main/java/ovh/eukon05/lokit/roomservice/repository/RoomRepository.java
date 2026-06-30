package ovh.eukon05.lokit.roomservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.eukon05.lokit.roomservice.model.RoomEntity;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
    List<RoomEntity> findAllByAclContains(UUID roleId);
}
