package ovh.eukon05.lokit.cardservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCardDTO(@NotBlank @Size(max = 100) String name) {
}
