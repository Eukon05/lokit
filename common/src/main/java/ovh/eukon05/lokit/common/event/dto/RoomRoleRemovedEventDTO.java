package ovh.eukon05.lokit.common.event.dto;

import java.time.Instant;
import java.util.UUID;

public record RoomRoleRemovedEventDTO(Instant timestamp, UUID roomId, UUID roleId) {
}
