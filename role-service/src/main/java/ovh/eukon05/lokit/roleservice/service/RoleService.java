package ovh.eukon05.lokit.roleservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;

import java.util.UUID;

public interface RoleService {
    RoleEntity findById(UUID id);

    UUID saveRole(RoleEntity role);

    Page<RoleEntity> findAll(Pageable pageable);
}
