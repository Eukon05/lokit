package ovh.eukon05.lokit.roleservice.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.UUID;

public record FindAllByIdDTO(@NotEmpty Set<UUID> cardIds) {
}
