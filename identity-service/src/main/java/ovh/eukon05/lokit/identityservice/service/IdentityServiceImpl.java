package ovh.eukon05.lokit.identityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.identityservice.adapter.IdpAdapter;
import ovh.eukon05.lokit.identityservice.adapter.IdpUserMetadata;
import ovh.eukon05.lokit.identityservice.model.IdpUserEntity;
import ovh.eukon05.lokit.identityservice.repository.IdpUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {
    private final IdpUserRepository repository;
    private final IdpAdapter idpAdapter;

    @Override
    public boolean checkUserExists(UUID userId) {
        return repository.existsById(userId);
    }

    @Override
    public IdpUserEntity getUser(UUID userId) {
        return repository.findById(userId).orElse(null);

    }

    @Override
    public List<IdpUserEntity> getUsers() {
        return repository.findAll();
    }

    @Override
    public void idpSync() {
        List<IdpUserMetadata> idpUsers = idpAdapter.getAllUsers();

        for (IdpUserMetadata idpUser : idpUsers) {
            Optional<IdpUserEntity> savedUser = repository.findByIdpId(idpUser.idpId());
            savedUser.ifPresentOrElse(u -> {
                if (!u.getEmail().equals(idpUser.email()) || !u.getFirstName().equals(idpUser.firstName()) || !u.getLastName().equals(idpUser.lastName())) {
                    u.setEmail(idpUser.email());
                    u.setFirstName(idpUser.firstName());
                    u.setLastName(idpUser.lastName());

                    repository.save(u);
                }
            }, () -> {
                IdpUserEntity newUser = new IdpUserEntity();
                newUser.setIdpId(idpUser.idpId());
                newUser.setEmail(idpUser.email());
                newUser.setFirstName(idpUser.firstName());
                newUser.setLastName(idpUser.lastName());
                repository.save(newUser);
            });
        }
    }
}
