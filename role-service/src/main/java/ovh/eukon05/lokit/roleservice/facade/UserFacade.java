package ovh.eukon05.lokit.roleservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.UserRoleAddedEventDTO;
import ovh.eukon05.lokit.common.dto.event.UserRoleRemovedEventDTO;
import ovh.eukon05.lokit.roleservice.client.EventClient;
import ovh.eukon05.lokit.roleservice.dto.response.GetUserDTO;
import ovh.eukon05.lokit.roleservice.mapper.UserMapper;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;
import ovh.eukon05.lokit.roleservice.service.RoleService;
import ovh.eukon05.lokit.roleservice.service.UserService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final EventClient eventClient;
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public GetUserDTO getUser(UUID userId) {
        return userMapper.toGetUserDTO(userService.getUser(userId));
    }

    public void assignRole(UUID userId, UUID roleId) {
        RoleEntity role = roleService.findById(roleId);
        userService.assignRoleToUser(userId, role);
        eventClient.sendUserRoleAddedEvent(new UserRoleAddedEventDTO(Instant.now(), userId, roleId));
    }

    public void removeRoleFromUser(UUID userId, UUID roleId) {
        RoleEntity role = roleService.findById(roleId);
        userService.removeRoleFromUser(userId, role);
        eventClient.sendUserRoleRemovedEvent(new UserRoleRemovedEventDTO(Instant.now(), userId, roleId));
    }
}
