package ovh.eukon05.lokit.decisionservice.exception;

public class CardWithoutUserException extends RuntimeException {
    private static final String MESSAGE = "Card %s has no user assigned to it";

    public CardWithoutUserException(String cardId) {
        super(MESSAGE.formatted(cardId));
    }
}
