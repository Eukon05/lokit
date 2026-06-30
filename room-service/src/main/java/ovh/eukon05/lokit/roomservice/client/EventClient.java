package ovh.eukon05.lokit.roomservice.client;

import ovh.eukon05.lokit.common.dto.event.RoleDeletedEventDTO;

public interface EventClient {
    void receiveRoleDeletedEvent(RoleDeletedEventDTO event);
}
