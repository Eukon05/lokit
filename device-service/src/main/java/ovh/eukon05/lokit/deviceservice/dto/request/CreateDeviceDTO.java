package ovh.eukon05.lokit.deviceservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateDeviceDTO(@NotBlank String name, @NotBlank String description,
                              @NotBlank String physicalAddress, @NotNull UUID roomId) {
}
