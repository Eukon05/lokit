package ovh.eukon05.lokit.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.eukon05.lokit.identityservice.model.IdpUserEntity;

import java.util.Optional;
import java.util.UUID;

public interface IdpUserRepository extends JpaRepository<IdpUserEntity, UUID> {
    Optional<IdpUserEntity> findByIdpId(String idpId);
}
