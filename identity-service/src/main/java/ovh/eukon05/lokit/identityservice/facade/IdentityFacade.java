package ovh.eukon05.lokit.identityservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.identityservice.dto.response.GetUserDTO;
import ovh.eukon05.lokit.identityservice.mapper.IdpUserMapper;
import ovh.eukon05.lokit.identityservice.model.IdpUserEntity;
import ovh.eukon05.lokit.identityservice.service.IdentityService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdentityFacade {
    private final IdentityService service;
    private final IdpUserMapper mapper;

    public GetUserDTO getUser(UUID userId) {
        return mapper.toGetUserDTO(service.getUser(userId));
    }

    public GetUserDTO getIdpUser(String userIdpId) {
        IdpUserEntity entity = service.getUserByIdpId(userIdpId);
        return mapper.toGetUserDTO(entity);
    }

    public PagedModel<GetUserDTO> getUsers(Pageable pageable) {
        return new PagedModel<>(service.getUsers(pageable).map(mapper::toGetUserDTO));
    }

    public void idpSync() {
        service.idpSync();
    }

}
