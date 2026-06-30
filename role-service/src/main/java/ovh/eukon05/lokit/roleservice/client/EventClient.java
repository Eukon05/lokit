package ovh.eukon05.lokit.roleservice.client;

import ovh.eukon05.lokit.common.dto.event.RoleDeletedEventDTO;

public interface EventClient {
    void sendRoleDeletedEvent(RoleDeletedEventDTO dto);
}
