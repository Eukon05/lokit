package ovh.eukon05.lokit.decisionservice.client;

import ovh.eukon05.lokit.decisionservice.model.RoomState;

import java.util.List;

public interface RoomServiceClient {
    List<RoomState> listActiveRooms();
}
