package ovh.eukon05.lokit.deviceservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateDeviceDTO(@NotBlank String name, @NotBlank String description,
                              @NotBlank String physicalAddress) {
}
