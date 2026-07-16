package ovh.eukon05.lokit.identityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.event.dto.UserDeletedEventDTO;
import ovh.eukon05.lokit.identityservice.adapter.IdpAdapter;
import ovh.eukon05.lokit.identityservice.adapter.IdpUserMetadata;
import ovh.eukon05.lokit.identityservice.client.EventClient;
import ovh.eukon05.lokit.identityservice.exception.UserNotFoundException;
import ovh.eukon05.lokit.identityservice.model.IdpUserEntity;
import ovh.eukon05.lokit.identityservice.repository.IdpUserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class IdentityServiceImpl implements IdentityService {
    private final IdpUserRepository repository;
    private final IdpAdapter idpAdapter;
    private final EventClient eventClient;

    @Override
    public IdpUserEntity getUser(UUID userId) {
        return repository.findById(userId).orElseThrow(UserNotFoundException::new);

    }

    @Override
    public IdpUserEntity getUserByIdpId(String userIdpId) {
        Optional<IdpUserEntity> entity = repository.findByIdpId(userIdpId);
        Optional<IdpUserMetadata> idpMetadata = idpAdapter.getUserMetadata(userIdpId);

        if (entity.isPresent() && idpMetadata.isPresent()) {
            IdpUserEntity idpUserEntity = entity.get();
            IdpUserMetadata idpUserMetadata = idpMetadata.get();

            updateUserFromIdpMetadata(idpUserEntity, idpUserMetadata);
            return idpUserEntity;
        } else if (entity.isEmpty() && idpMetadata.isPresent()) {
            return createUserFromIdpMetadata(idpMetadata.get());
        } else {
            entity.ifPresent(this::deleteUser);
            throw new UserNotFoundException();
        }
    }

    @Override
    public Page<IdpUserEntity> getUsers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void idpSync() {
        List<IdpUserMetadata> idpUsers = idpAdapter.getAllUsers();
        Set<String> idpUserIds = idpUsers.stream()
                .map(IdpUserMetadata::idpId)
                .collect(Collectors.toSet());

        for (IdpUserMetadata idpUser : idpUsers) {
            Optional<IdpUserEntity> savedUser = repository.findByIdpId(idpUser.idpId());
            savedUser.ifPresentOrElse(u -> updateUserFromIdpMetadata(u, idpUser), () -> createUserFromIdpMetadata(idpUser));
        }

        repository.findAll().stream()
                .filter(user -> !idpUserIds.contains(user.getIdpId()))
                .forEach(this::deleteUser);
    }

    private void updateUserFromIdpMetadata(IdpUserEntity entity, IdpUserMetadata idpMeta) {
        if (!entity.getEmail().equals(idpMeta.email()) || !entity.getFirstName().equals(idpMeta.firstName()) || !entity.getLastName().equals(idpMeta.lastName())) {
            entity.setEmail(idpMeta.email());
            entity.setFirstName(idpMeta.firstName());
            entity.setLastName(idpMeta.lastName());
            repository.save(entity);
        }
    }

    private IdpUserEntity createUserFromIdpMetadata(IdpUserMetadata idpMeta) {
        IdpUserEntity newUser = new IdpUserEntity();
        newUser.setIdpId(idpMeta.idpId());
        newUser.setEmail(idpMeta.email());
        newUser.setFirstName(idpMeta.firstName());
        newUser.setLastName(idpMeta.lastName());
        return repository.save(newUser);
    }

    private void deleteUser(IdpUserEntity user) {
        repository.delete(user);
        eventClient.sendUserDeletedEvent(new UserDeletedEventDTO(Instant.now(), user.getId()));
    }
}
