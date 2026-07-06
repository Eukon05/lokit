package ovh.eukon05.lokit.deviceservice.exception;

public class DeviceNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Device not found";

    public DeviceNotFoundException() {
        super(MESSAGE);
    }
}
