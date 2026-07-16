package ovh.eukon05.lokit.identityservice.adapter;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.identityservice.mapper.IdpUserMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class IdpAdapterImpl implements IdpAdapter {
    @Value("${lokit.keycloak.realm}")
    private String keycloakRealm;
    private final Keycloak keycloak;
    private final IdpUserMapper idpUserMapper;

    @Override
    public Optional<IdpUserMetadata> getUserMetadata(String userIdpId) {
        try {
            UserRepresentation user = keycloak.realm(keycloakRealm).users().get(userIdpId).toRepresentation();
            return Optional.of(idpUserMapper.toIdpUserMetadata(user));
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<IdpUserMetadata> getAllUsers() {
        return keycloak
                .realm(keycloakRealm)
                .users()
                .list()
                .stream()
                .map(idpUserMapper::toIdpUserMetadata)
                .toList();
    }
}
