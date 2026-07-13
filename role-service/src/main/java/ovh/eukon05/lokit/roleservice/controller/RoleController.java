package ovh.eukon05.lokit.roleservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ovh.eukon05.lokit.roleservice.dto.request.CreateRoleDTO;
import ovh.eukon05.lokit.roleservice.dto.response.GetRoleDTO;
import ovh.eukon05.lokit.roleservice.facade.RoleFacade;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
@PreAuthorize("hasRole('LOKIT_ADMIN')")
public class RoleController {
    private final RoleFacade roleFacade;

    @GetMapping("/{roleId}")
    public GetRoleDTO findById(@PathVariable UUID roleId) {
        return roleFacade.getRole(roleId);
    }

    @PostMapping
    public UUID createRole(@RequestBody @Valid CreateRoleDTO roleDTO) {
        return roleFacade.createRole(roleDTO);
    }

    @GetMapping
    public PagedModel<GetRoleDTO> findAll(Pageable pageable) {
        return roleFacade.findAll(pageable);
    }

    @DeleteMapping("/{roleId}")
    public void deleteRole(@PathVariable UUID roleId) {
        roleFacade.deleteRole(roleId);
    }

    @PostMapping("/{roleId}/enable")
    public GetRoleDTO enableRole(@PathVariable UUID roleId) {
        return roleFacade.enableRole(roleId);
    }

    @PostMapping("/{roleId}/disable")
    public GetRoleDTO disableRole(@PathVariable UUID roleId) {
        return roleFacade.disableRole(roleId);
    }
}
