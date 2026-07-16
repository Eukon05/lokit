package ovh.eukon05.lokit.cardservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.cardservice.client.IdentityServiceClient;
import ovh.eukon05.lokit.cardservice.exception.UserNotFoundException;
import ovh.eukon05.lokit.cardservice.model.UserEntity;
import ovh.eukon05.lokit.cardservice.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final IdentityServiceClient identityClient;

    @Override
    public UserEntity getUser(UUID userId) {
        return userRepository.findById(userId).orElseGet(() -> {
            if (identityClient.checkUserExists(userId)) {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(userId);
                return userRepository.save(userEntity);
            } else throw new UserNotFoundException();
        });
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.findById(userId).ifPresent(userRepository::delete);
    }
}
