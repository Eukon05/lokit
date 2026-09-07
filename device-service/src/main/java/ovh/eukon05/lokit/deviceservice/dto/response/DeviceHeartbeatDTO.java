package ovh.eukon05.lokit.deviceservice.dto.response;

import java.time.Instant;

public record DeviceHeartbeatDTO(String physicalAddress, Instant timestamp) {
}
