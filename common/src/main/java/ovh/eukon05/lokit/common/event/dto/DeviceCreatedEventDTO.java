package ovh.eukon05.lokit.common.event.dto;

import java.time.Instant;
import java.util.UUID;

public record DeviceCreatedEventDTO(Instant timestamp, UUID deviceId) {
}
