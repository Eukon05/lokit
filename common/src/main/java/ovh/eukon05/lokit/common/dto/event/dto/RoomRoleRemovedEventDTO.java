package ovh.eukon05.lokit.common.dto.event.dto;

import java.time.Instant;
import java.util.UUID;

public record RoomRoleRemovedEventDTO(Instant timestamp, UUID roomId, UUID roleId) {
}
