package ovh.eukon05.lokit.roleservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;
import ovh.eukon05.lokit.roleservice.service.RoleService;
import ovh.eukon05.lokit.roleservice.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final RoleService roleService;

    public void assignRole(UUID userId, UUID roleId) {
        RoleEntity role = roleService.findById(roleId);
        userService.assignRoleToUser(userId, role);
    }

    public void removeRoleFromUser(UUID userId, UUID roleId) {
        RoleEntity role = roleService.findById(roleId);
        userService.removeRoleFromUser(userId, role);
    }
}
