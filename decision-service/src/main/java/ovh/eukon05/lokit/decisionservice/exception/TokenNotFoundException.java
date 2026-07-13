package ovh.eukon05.lokit.decisionservice.exception;

public class TokenNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Token not found";

    public TokenNotFoundException() {
        super(MESSAGE);
    }
}
