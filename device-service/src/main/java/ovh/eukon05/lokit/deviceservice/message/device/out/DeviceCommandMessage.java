package ovh.eukon05.lokit.deviceservice.message.device.out;

import ovh.eukon05.lokit.deviceservice.model.DeviceCommandType;

import java.time.Instant;
import java.util.UUID;

public record DeviceCommandMessage(UUID id, DeviceCommandType command, Instant issuedAt, Instant expiresAt) {
}
