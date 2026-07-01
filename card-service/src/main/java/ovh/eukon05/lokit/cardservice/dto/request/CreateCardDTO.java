package ovh.eukon05.lokit.cardservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateCardDTO(@NotNull UUID id, @NotNull UUID userId, @NotBlank String name) {
}
