package ovh.eukon05.lokit.common.event.dto;

import java.time.Instant;

public record CardDisabledEventDTO(Instant timestamp, String cardId) {
}
