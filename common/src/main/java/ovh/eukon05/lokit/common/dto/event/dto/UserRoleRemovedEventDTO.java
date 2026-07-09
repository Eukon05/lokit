package ovh.eukon05.lokit.common.dto.event.dto;

import java.time.Instant;
import java.util.UUID;

public record UserRoleRemovedEventDTO(Instant timestamp, UUID userId, UUID roleId) {
}
