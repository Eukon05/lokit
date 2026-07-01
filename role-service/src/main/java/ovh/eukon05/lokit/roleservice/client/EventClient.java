package ovh.eukon05.lokit.roleservice.client;

import ovh.eukon05.lokit.common.dto.event.RoleDeletedEventDTO;
import ovh.eukon05.lokit.common.dto.event.RoleDisabledEventDTO;
import ovh.eukon05.lokit.common.dto.event.RoleEnabledEventDTO;

public interface EventClient {
    void sendRoleDeletedEvent(RoleDeletedEventDTO dto);

    void sendRoleEnabledEvent(RoleEnabledEventDTO dto);

    void sendRoleDisabledEvent(RoleDisabledEventDTO dto);
}
