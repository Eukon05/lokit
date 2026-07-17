package ovh.eukon05.lokit.cardservice.dto.response;

import java.util.Set;
import java.util.UUID;

public record GetUserDTO(UUID id, Set<String> cards) {
}
