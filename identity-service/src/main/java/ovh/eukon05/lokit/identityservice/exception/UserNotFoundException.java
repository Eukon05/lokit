package ovh.eukon05.lokit.identityservice.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
