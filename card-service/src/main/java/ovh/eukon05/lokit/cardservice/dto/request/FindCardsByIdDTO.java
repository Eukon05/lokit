package ovh.eukon05.lokit.cardservice.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record FindCardsByIdDTO(@NotEmpty Set<String> cardIds) {
}
