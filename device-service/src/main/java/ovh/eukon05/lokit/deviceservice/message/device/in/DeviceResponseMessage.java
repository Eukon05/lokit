package ovh.eukon05.lokit.deviceservice.message.device.in;

import java.util.UUID;

public record DeviceResponseMessage(UUID commandId, DeviceResponseType response) {
}
