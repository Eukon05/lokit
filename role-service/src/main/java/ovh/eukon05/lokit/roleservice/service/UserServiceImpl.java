package ovh.eukon05.lokit.roleservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.roleservice.client.IdentityServiceClient;
import ovh.eukon05.lokit.roleservice.exception.UserNotFoundException;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;
import ovh.eukon05.lokit.roleservice.model.UserEntity;
import ovh.eukon05.lokit.roleservice.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IdentityServiceClient identityClient;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void assignRoleToUser(UUID userId, RoleEntity role) {
        UserEntity user = getUser(userId);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeRoleFromUser(UUID userId, RoleEntity role) {
        UserEntity user = getUser(userId);
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    private UserEntity getUser(UUID userId) {
        return userRepository.findById(userId).orElseGet(() -> {
            if (identityClient.checkUserExists(userId)) {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(userId);
                return userRepository.save(userEntity);
            } else throw new UserNotFoundException();
        });
    }
}
