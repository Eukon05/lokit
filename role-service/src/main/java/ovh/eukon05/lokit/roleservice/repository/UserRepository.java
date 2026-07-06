package ovh.eukon05.lokit.roleservice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ovh.eukon05.lokit.roleservice.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Override
    @EntityGraph(attributePaths = "roles")
    List<UserEntity> findAll();
}
