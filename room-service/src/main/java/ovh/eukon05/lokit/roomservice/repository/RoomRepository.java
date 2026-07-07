package ovh.eukon05.lokit.roomservice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ovh.eukon05.lokit.roomservice.model.RoomEntity;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
    List<RoomEntity> findAllByAclContains(UUID roleId);

    @EntityGraph(attributePaths = "acl")
    List<RoomEntity> findAllByActiveTrue();

    boolean existsByIdAndActiveTrue(UUID id);
}
