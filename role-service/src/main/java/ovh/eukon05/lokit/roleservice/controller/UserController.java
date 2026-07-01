package ovh.eukon05.lokit.roleservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ovh.eukon05.lokit.roleservice.dto.response.GetUserDTO;
import ovh.eukon05.lokit.roleservice.facade.UserFacade;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;

    @GetMapping("/{userId}")
    public GetUserDTO getUser(@PathVariable UUID userId) {
        return userFacade.getUser(userId);
    }

    @PostMapping("/{userId}/role/{roleId}")
    public void assignRole(@PathVariable UUID userId, @PathVariable UUID roleId) {
        userFacade.assignRole(userId, roleId);
    }

    @DeleteMapping("/{userId}/role/{roleId}")
    public void removeRole(@PathVariable UUID userId, @PathVariable UUID roleId) {
        userFacade.removeRoleFromUser(userId, roleId);
    }
}
