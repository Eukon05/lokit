package ovh.eukon05.lokit.identityservice.dto.response;

import java.util.UUID;

public record GetUserDTO(UUID id, String firstName, String lastName, String email) {
}
