package ovh.eukon05.lokit.roomservice.client;

import ovh.eukon05.lokit.common.event.dto.*;

public interface EventClient {
    void sendRoomCreatedEvent(RoomCreatedEventDTO event);

    void sendRoleAddedToACLEvent(RoomRoleAddedEventDTO event);

    void sendRoleRemovedFromACLEvent(RoomRoleRemovedEventDTO event);

    void sendRoomEnabledEvent(RoomEnabledEventDTO event);

    void sendRoomDisabledEvent(RoomDisabledEventDTO event);
}
