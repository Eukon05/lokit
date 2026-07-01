package ovh.eukon05.lokit.cardservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ovh.eukon05.lokit.cardservice.dto.response.GetUserDTO;
import ovh.eukon05.lokit.cardservice.facade.UserFacade;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;

    @GetMapping("/{userId}")
    public GetUserDTO getUser(@PathVariable UUID userId) {
        return userFacade.getUser(userId);
    }

}
