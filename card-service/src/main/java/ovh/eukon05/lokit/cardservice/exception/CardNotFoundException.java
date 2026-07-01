package ovh.eukon05.lokit.cardservice.exception;

import java.util.UUID;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(UUID cardId) {
        super("Card with id %s not found".formatted(cardId));
    }
}
