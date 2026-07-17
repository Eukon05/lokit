package ovh.eukon05.lokit.common.event.dto;

import java.time.Instant;

public record CardDeletedEventDTO(Instant timestamp, String cardId) {
}
