package ovh.eukon05.lokit.cardservice.dto.response;

import java.time.Instant;
import java.util.UUID;

public record GetCardDTO(UUID id, UUID userId, String name, boolean active, Instant createdAt, Instant updatedAt) {
}
