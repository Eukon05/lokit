package ovh.eukon05.lokit.roleservice.service;

import ovh.eukon05.lokit.roleservice.dto.request.CreateRoleDTO;
import ovh.eukon05.lokit.roleservice.dto.response.GetRoleDTO;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;

import java.util.UUID;

public interface RoleService {
    GetRoleDTO findById(UUID id);
    UUID createRole(CreateRoleDTO role);
}
