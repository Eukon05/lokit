package ovh.eukon05.lokit.roleservice.service;

import ovh.eukon05.lokit.roleservice.model.RoleEntity;

import java.util.UUID;

public interface UserService {
    void assignRoleToUser(UUID userId, RoleEntity role);

    void removeRoleFromUser(UUID userId, RoleEntity role);
}
