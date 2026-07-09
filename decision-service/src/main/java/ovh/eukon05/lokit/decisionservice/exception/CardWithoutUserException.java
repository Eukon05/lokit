package ovh.eukon05.lokit.decisionservice.exception;

import java.util.UUID;

public class CardWithoutUserException extends RuntimeException {
    private static final String MESSAGE = "Card %s has not user assigned to it";

    public CardWithoutUserException(UUID cardId) {
        super(MESSAGE.formatted(cardId));
    }
}
