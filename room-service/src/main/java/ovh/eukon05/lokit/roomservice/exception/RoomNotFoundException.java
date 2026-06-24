package ovh.eukon05.lokit.roomservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Room not found";

    public RoomNotFoundException() {
        super(MESSAGE);
    }
}
