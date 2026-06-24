package ovh.eukon05.lokit.roleservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateRoleDTO(@NotBlank String name, @NotBlank String description) {
}
