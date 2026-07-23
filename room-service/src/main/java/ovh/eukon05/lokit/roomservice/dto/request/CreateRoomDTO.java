package ovh.eukon05.lokit.roomservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateRoomDTO(@NotBlank @Size(max = 100) String name,
                            @NotBlank @Size(max = 500) String description) {
}
