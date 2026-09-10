package ovh.eukon05.lokit.deviceservice.message.in;

import java.time.Instant;

public record DeviceHeartbeatMessage(String physicalAddress, Instant timestamp) {
}
