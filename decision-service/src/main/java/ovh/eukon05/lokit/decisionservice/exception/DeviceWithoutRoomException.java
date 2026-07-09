package ovh.eukon05.lokit.decisionservice.exception;

import java.util.UUID;

public class DeviceWithoutRoomException extends RuntimeException {
    private static final String MESSAGE = "Device %s has no room assigned to it";

    public DeviceWithoutRoomException(UUID deviceId) {
        super(MESSAGE.formatted(deviceId));
    }
}
