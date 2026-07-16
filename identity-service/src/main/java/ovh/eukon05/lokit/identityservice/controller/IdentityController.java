package ovh.eukon05.lokit.identityservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ovh.eukon05.lokit.identityservice.dto.response.GetUserDTO;
import ovh.eukon05.lokit.identityservice.facade.IdentityFacade;

import java.util.UUID;

@RestController
@RequestMapping("/identity")
@RequiredArgsConstructor
public class IdentityController {
    private final IdentityFacade facade;

    @GetMapping("/me")
    public GetUserDTO getMe(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return facade.getIdpUser(principal.getAttribute("sub"));
    }

    @GetMapping("/{userId}")
    public GetUserDTO getUsers(@PathVariable UUID userId) {
        return facade.getUser(userId);
    }

    @GetMapping
    public PagedModel<GetUserDTO> getUsers(Pageable pageable) {
        return facade.getUsers(pageable);
    }

    @GetMapping("/sync")
    public void idpSync() {
        facade.idpSync();
    }
}
