package ovh.eukon05.lokit.roleservice.client;

import ovh.eukon05.lokit.common.event.dto.*;

public interface EventClient {
    void sendRoleCreatedEvent(RoleCreatedEventDTO dto);

    void sendRoleDeletedEvent(RoleDeletedEventDTO dto);

    void sendRoleEnabledEvent(RoleEnabledEventDTO dto);

    void sendRoleDisabledEvent(RoleDisabledEventDTO dto);

    void sendUserRoleAddedEvent(UserRoleAddedEventDTO dto);

    void sendUserRoleRemovedEvent(UserRoleRemovedEventDTO dto);
}
