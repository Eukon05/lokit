package ovh.eukon05.lokit.deviceservice.exception;

public class DeviceCommandNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Device command not found";

    public DeviceCommandNotFoundException() {
        super(MESSAGE);
    }
}
