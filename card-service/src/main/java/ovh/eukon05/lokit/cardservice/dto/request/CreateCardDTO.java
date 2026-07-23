package ovh.eukon05.lokit.cardservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateCardDTO(@NotBlank @Size(min = 8, max = 8) String id, @NotNull UUID userId,
                            @NotBlank @Size(max = 100) String name) {
}
