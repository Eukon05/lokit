package ovh.eukon05.lokit.common.dto.event;

import java.time.Instant;
import java.util.UUID;

public record DeviceEnabledEventDTO(Instant timestamp, UUID deviceId, UUID roomId) {
}
