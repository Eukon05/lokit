package ovh.eukon05.lokit.roleservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ovh.eukon05.lokit.roleservice.dto.request.CreateRoleDTO;
import ovh.eukon05.lokit.roleservice.dto.response.GetRoleDTO;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    GetRoleDTO toGetRoleDTO(RoleEntity roleEntity);
    RoleEntity fromCreateRoleDTO(CreateRoleDTO createRoleDTO);

}
