package ovh.eukon05.lokit.roleservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.roleservice.dto.request.CreateRoleDTO;
import ovh.eukon05.lokit.roleservice.dto.response.GetRoleDTO;
import ovh.eukon05.lokit.roleservice.exception.RoleNotFoundException;
import ovh.eukon05.lokit.roleservice.mapper.RoleMapper;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;
import ovh.eukon05.lokit.roleservice.repository.RoleRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public GetRoleDTO findById(UUID id) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(id);

        if (roleEntity.isPresent()) return roleMapper.toGetRoleDTO(roleEntity.get());
        else throw new RoleNotFoundException();
    }

    @Override
    public UUID createRole(CreateRoleDTO role) {
        RoleEntity roleEntity = roleMapper.fromCreateRoleDTO(role);
        roleRepository.save(roleEntity);
        return roleEntity.getId();
    }
}
