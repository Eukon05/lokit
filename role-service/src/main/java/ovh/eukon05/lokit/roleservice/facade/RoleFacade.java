package ovh.eukon05.lokit.roleservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.roleservice.dto.request.CreateRoleDTO;
import ovh.eukon05.lokit.roleservice.dto.response.GetRoleDTO;
import ovh.eukon05.lokit.roleservice.mapper.RoleMapper;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;
import ovh.eukon05.lokit.roleservice.service.RoleService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleFacade {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public GetRoleDTO getRole(UUID id) {
        RoleEntity role = roleService.findById(id);
        return roleMapper.toGetRoleDTO(role);
    }

    public UUID createRole(CreateRoleDTO roleDTO) {
        RoleEntity en = roleMapper.fromCreateRoleDTO(roleDTO);
        return roleService.saveRole(en);
    }

    public void deleteRole(UUID id) {
        roleService.deleteRole(id);
    }

    public PagedModel<GetRoleDTO> findAll(Pageable pageable) {
        return new PagedModel<>(roleService.findAll(pageable).map(roleMapper::toGetRoleDTO));
    }
}
