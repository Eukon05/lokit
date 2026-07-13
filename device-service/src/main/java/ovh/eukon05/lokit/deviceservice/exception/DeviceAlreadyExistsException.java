package ovh.eukon05.lokit.deviceservice.exception;

public class DeviceAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE = "Device with this physical address already exists!";

    public DeviceAlreadyExistsException() {
        super(MESSAGE);
    }
}
