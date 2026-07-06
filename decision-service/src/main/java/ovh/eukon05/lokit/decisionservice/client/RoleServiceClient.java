package ovh.eukon05.lokit.decisionservice.client;

import ovh.eukon05.lokit.decisionservice.model.UserState;

import java.util.List;
import java.util.UUID;

public interface RoleServiceClient {
    List<UUID> listActiveRoles();

    List<UserState> listUserRoles();
}
