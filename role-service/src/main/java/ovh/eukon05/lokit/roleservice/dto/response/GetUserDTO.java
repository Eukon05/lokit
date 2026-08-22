package ovh.eukon05.lokit.roleservice.dto.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record GetUserDTO(UUID id, Set<UUID> roles, Instant createdAt, Instant updatedAt) {
}
