package ovh.eukon05.lokit.roomservice.client;

import ovh.eukon05.lokit.common.dto.event.RoleDeletedEventDTO;
import ovh.eukon05.lokit.common.dto.event.RoomDisabledEventDTO;
import ovh.eukon05.lokit.common.dto.event.RoomEnabledEventDTO;

public interface EventClient {
    void receiveRoleDeletedEvent(RoleDeletedEventDTO event);

    void sendRoomEnabledEvent(RoomEnabledEventDTO event);

    void sendRoomDisabledEvent(RoomDisabledEventDTO event);
}
