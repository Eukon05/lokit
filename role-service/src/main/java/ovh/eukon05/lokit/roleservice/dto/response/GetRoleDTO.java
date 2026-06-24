package ovh.eukon05.lokit.roleservice.dto.response;

import java.time.Instant;
import java.util.UUID;

public record GetRoleDTO(UUID id, String name, String description, Instant createdAt, Instant updatedAt) {
}
