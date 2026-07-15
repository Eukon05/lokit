package ovh.eukon05.lokit.identityservice.service;

import ovh.eukon05.lokit.identityservice.model.IdpUserEntity;

import java.util.List;
import java.util.UUID;

public interface IdentityService {
    boolean checkUserExists(UUID userId);

    IdpUserEntity getUser(UUID userId);

    List<IdpUserEntity> getUsers();

    void idpSync();
}
