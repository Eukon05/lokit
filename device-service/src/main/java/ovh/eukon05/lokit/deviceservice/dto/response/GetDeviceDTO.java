package ovh.eukon05.lokit.deviceservice.dto.response;

import java.util.UUID;

public record GetDeviceDTO(UUID id, String name, String description, String physicalAddress, boolean hasActiveToken,
                           UUID roomId) {
}
