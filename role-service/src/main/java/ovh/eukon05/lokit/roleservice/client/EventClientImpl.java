package ovh.eukon05.lokit.roleservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.RabbitEventPublisher;
import ovh.eukon05.lokit.common.dto.event.dto.*;

import static ovh.eukon05.lokit.common.dto.config.RabbitConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventClientImpl implements EventClient {
    private final RabbitEventPublisher publisher;

    @Override
    public void sendRoleCreatedEvent(RoleCreatedEventDTO dto) {
        publisher.sendObject(ROLE_CREATED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoleDeletedEvent(RoleDeletedEventDTO dto) {
        publisher.sendObject(ROLE_DELETED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoleEnabledEvent(RoleEnabledEventDTO dto) {
        publisher.sendObject(ROLE_ENABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendRoleDisabledEvent(RoleDisabledEventDTO dto) {
        publisher.sendObject(ROLE_DISABLED_ROUTING_KEY, dto);
    }

    @Override
    public void sendUserRoleAddedEvent(UserRoleAddedEventDTO dto) {
        publisher.sendObject(USER_ROLE_ASSIGNED_ROUTING_KEY, dto);
    }

    @Override
    public void sendUserRoleRemovedEvent(UserRoleRemovedEventDTO dto) {
        publisher.sendObject(USER_ROLE_REMOVED_ROUTING_KEY, dto);
    }
}
