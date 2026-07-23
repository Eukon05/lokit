package ovh.eukon05.lokit.deviceservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateDeviceDTO(@NotBlank @Size(max = 100) String name,
                              @NotBlank @Size(max = 500) String description,
                              @NotBlank @Size(min = 17, max = 17) @Pattern(regexp = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})|([0-9a-fA-F]{4}\\.[0-9a-fA-F]{4}\\.[0-9a-fA-F]{4})$") String physicalAddress) {
}
