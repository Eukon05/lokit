package ovh.eukon05.lokit.cardservice.service;

import ovh.eukon05.lokit.cardservice.model.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity getUser(UUID userId);
}
