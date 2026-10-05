package ovh.eukon05.lokit.deviceservice.dto.request;

import ovh.eukon05.lokit.deviceservice.model.DeviceCommandType;

public record SendDeviceCommandDTO(DeviceCommandType command) {
}
