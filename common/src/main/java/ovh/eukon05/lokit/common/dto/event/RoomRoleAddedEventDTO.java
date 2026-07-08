package ovh.eukon05.lokit.common.dto.event;

import java.time.Instant;
import java.util.UUID;

public record RoomRoleAddedEventDTO(Instant timestamp, UUID roomId, UUID roleId) {
}
