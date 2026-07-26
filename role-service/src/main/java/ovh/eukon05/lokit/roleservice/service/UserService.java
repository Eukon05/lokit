package ovh.eukon05.lokit.roleservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;
import ovh.eukon05.lokit.roleservice.model.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity getUser(UUID userId);

    Page<UserEntity> getUsers(Pageable pageable);

    void assignRoleToUser(UUID userId, RoleEntity role);

    void removeRoleFromUser(UUID userId, RoleEntity role);

    void deleteUser(UUID userId);
}
