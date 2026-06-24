package ovh.eukon05.lokit.roomservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateRoomDTO(@NotBlank String name, @NotBlank String description) {
}
