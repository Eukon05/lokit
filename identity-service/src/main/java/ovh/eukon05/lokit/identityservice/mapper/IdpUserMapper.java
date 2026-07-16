package ovh.eukon05.lokit.identityservice.mapper;

import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ovh.eukon05.lokit.identityservice.adapter.IdpUserMetadata;
import ovh.eukon05.lokit.identityservice.dto.response.GetUserDTO;
import ovh.eukon05.lokit.identityservice.model.IdpUserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IdpUserMapper {
    @Mapping(source = "id", target = "idpId")
    IdpUserMetadata toIdpUserMetadata(UserRepresentation user);

    GetUserDTO toGetUserDTO(IdpUserEntity entity);
}
