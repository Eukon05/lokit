package ovh.eukon05.lokit.roleservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    boolean existsByIdAndActiveTrue(UUID id);

    List<RoleEntity> findAllByActiveTrue();
}
