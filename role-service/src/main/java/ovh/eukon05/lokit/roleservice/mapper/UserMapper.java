package ovh.eukon05.lokit.roleservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ovh.eukon05.lokit.roleservice.dto.response.GetUserDTO;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;
import ovh.eukon05.lokit.roleservice.model.UserEntity;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(source = "roles", target = "roles")
    GetUserDTO toGetUserDTO(UserEntity userEntity);

    default Set<UUID> mapRoles(Set<RoleEntity> roles) {
        return roles.stream()
                .map(RoleEntity::getId)
                .collect(Collectors.toSet());
    }
}
