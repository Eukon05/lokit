package ovh.eukon05.lokit.common.event.dto;

import java.time.Instant;
import java.util.UUID;

public record CardEnabledEventDTO(Instant timestamp, String cardId, UUID userId) {
}
