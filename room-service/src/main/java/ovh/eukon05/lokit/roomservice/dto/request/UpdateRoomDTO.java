package ovh.eukon05.lokit.roomservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record UpdateRoomDTO(@NotBlank @Size(max = 100) Optional<String> name,
                            @NotBlank @Size(max = 500) Optional<String> description) {
}
