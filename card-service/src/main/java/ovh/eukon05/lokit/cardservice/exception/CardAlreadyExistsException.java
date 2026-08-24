package ovh.eukon05.lokit.cardservice.exception;

public class CardAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE = "Card with this ID already exists!";

    public CardAlreadyExistsException() {
        super(MESSAGE);
    }
}
