package ovh.eukon05.lokit.cardservice.dto.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record GetUserDTO(UUID id, Set<String> cards, Instant createdAt, Instant updatedAt) {
}
