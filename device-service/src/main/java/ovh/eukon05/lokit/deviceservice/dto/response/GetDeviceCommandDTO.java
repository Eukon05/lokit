package ovh.eukon05.lokit.deviceservice.dto.response;

import ovh.eukon05.lokit.deviceservice.model.DeviceCommandStatus;
import ovh.eukon05.lokit.deviceservice.model.DeviceCommandType;

import java.time.Instant;
import java.util.UUID;

public record GetDeviceCommandDTO(UUID id, UUID deviceId, DeviceCommandType type, DeviceCommandStatus status,
                                  Instant issuedAt, Instant expiresAt) {
}
