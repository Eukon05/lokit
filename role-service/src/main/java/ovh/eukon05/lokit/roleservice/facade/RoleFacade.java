package ovh.eukon05.lokit.roleservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.RoleCreatedEventDTO;
import ovh.eukon05.lokit.common.dto.event.RoleDeletedEventDTO;
import ovh.eukon05.lokit.common.dto.event.RoleDisabledEventDTO;
import ovh.eukon05.lokit.common.dto.event.RoleEnabledEventDTO;
import ovh.eukon05.lokit.roleservice.client.EventClient;
import ovh.eukon05.lokit.roleservice.dto.request.CreateRoleDTO;
import ovh.eukon05.lokit.roleservice.dto.response.GetRoleDTO;
import ovh.eukon05.lokit.roleservice.mapper.RoleMapper;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;
import ovh.eukon05.lokit.roleservice.service.RoleService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleFacade {
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final EventClient eventClient;

    public GetRoleDTO getRole(UUID id) {
        RoleEntity role = roleService.findById(id);
        return roleMapper.toGetRoleDTO(role);
    }

    public UUID createRole(CreateRoleDTO roleDTO) {
        RoleEntity en = roleMapper.fromCreateRoleDTO(roleDTO);
        UUID id = roleService.saveRole(en);
        eventClient.sendRoleCreatedEvent(new RoleCreatedEventDTO(Instant.now(), id));
        return id;
    }

    public void deleteRole(UUID id) {
        roleService.deleteRole(id);
        eventClient.sendRoleDeletedEvent(new RoleDeletedEventDTO(Instant.now(), id));
    }

    public GetRoleDTO enableRole(UUID id) {
        RoleEntity role = roleService.enableRole(id);
        eventClient.sendRoleEnabledEvent(new RoleEnabledEventDTO(Instant.now(), id));
        return roleMapper.toGetRoleDTO(role);
    }

    public GetRoleDTO disableRole(UUID id) {
        RoleEntity role = roleService.disableRole(id);
        eventClient.sendRoleDisabledEvent(new RoleDisabledEventDTO(Instant.now(), id));
        return roleMapper.toGetRoleDTO(role);
    }

    public PagedModel<GetRoleDTO> findAll(Pageable pageable) {
        return new PagedModel<>(roleService.findAll(pageable).map(roleMapper::toGetRoleDTO));
    }
}
