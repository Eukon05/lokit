package ovh.eukon05.lokit.roomservice.client;

import java.util.UUID;

public interface RoleServiceClient {
    boolean checkRoleExists(UUID roleId);
}
