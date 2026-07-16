package ovh.eukon05.lokit.roleservice.service;

import ovh.eukon05.lokit.roleservice.model.RoleEntity;
import ovh.eukon05.lokit.roleservice.model.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity getUser(UUID userId);

    void assignRoleToUser(UUID userId, RoleEntity role);

    void removeRoleFromUser(UUID userId, RoleEntity role);

    void deleteUser(UUID userId);
}
