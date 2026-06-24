package ovh.eukon05.lokit.roleservice.client;

import java.util.UUID;

public interface IdentityServiceClient {
    boolean checkUserExists(UUID userId);
}
