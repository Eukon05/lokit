package ovh.eukon05.lokit.deviceservice.client;

import java.util.UUID;

public interface RoomClient {
    boolean checkRoomExists(UUID roomId);
}
