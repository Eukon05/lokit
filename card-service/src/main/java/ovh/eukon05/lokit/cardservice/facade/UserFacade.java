package ovh.eukon05.lokit.cardservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.cardservice.dto.response.GetUserDTO;
import ovh.eukon05.lokit.cardservice.mapper.UserMapper;
import ovh.eukon05.lokit.cardservice.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;

    public GetUserDTO getUser(UUID userId) {
        return userMapper.toGetUserDTO(userService.getUser(userId));
    }
}
