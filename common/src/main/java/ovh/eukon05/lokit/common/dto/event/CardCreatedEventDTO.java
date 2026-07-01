package ovh.eukon05.lokit.common.dto.event;

import java.time.Instant;
import java.util.UUID;

public record CardCreatedEventDTO(Instant timestamp, UUID cardId, UUID userId) {
}
