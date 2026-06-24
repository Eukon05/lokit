package ovh.eukon05.lokit.roleservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ovh.eukon05.lokit.roleservice.dto.request.CreateRoleDTO;
import ovh.eukon05.lokit.roleservice.dto.response.GetRoleDTO;
import ovh.eukon05.lokit.roleservice.mapper.RoleMapper;
import ovh.eukon05.lokit.roleservice.service.RoleService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/{roleId}")
    public GetRoleDTO findById(@PathVariable UUID roleId) {
        return roleService.findById(roleId);
    }

    @PostMapping
    public UUID createRole(@RequestBody CreateRoleDTO roleDTO) {
        return roleService.createRole(roleDTO);
    }

}
