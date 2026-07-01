package ovh.eukon05.lokit.roleservice.dto.response;

import java.util.Set;
import java.util.UUID;

public record GetUserDTO(UUID id, Set<UUID> roles) {
}
