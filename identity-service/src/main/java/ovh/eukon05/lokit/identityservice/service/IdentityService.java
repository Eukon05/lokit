package ovh.eukon05.lokit.identityservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ovh.eukon05.lokit.identityservice.model.IdpUserEntity;

import java.util.UUID;

public interface IdentityService {

    IdpUserEntity getUser(UUID userId);

    IdpUserEntity getUserByIdpId(String userIdpId);

    Page<IdpUserEntity> getUsers(Pageable pageable);

    void idpSync();
}
