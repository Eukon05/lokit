package ovh.eukon05.lokit.cardservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.eukon05.lokit.cardservice.model.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
