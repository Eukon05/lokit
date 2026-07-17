package ovh.eukon05.lokit.cardservice.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String cardId) {
        super("Card with id %s not found".formatted(cardId));
    }
}
