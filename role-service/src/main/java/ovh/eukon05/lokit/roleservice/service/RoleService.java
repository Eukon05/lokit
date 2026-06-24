package ovh.eukon05.lokit.roleservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import ovh.eukon05.lokit.roleservice.dto.request.CreateRoleDTO;
import ovh.eukon05.lokit.roleservice.dto.response.GetRoleDTO;

import java.util.UUID;

public interface RoleService {
    GetRoleDTO findById(UUID id);
    UUID createRole(CreateRoleDTO role);

    PagedModel<GetRoleDTO> findAll(Pageable pageable);
}
