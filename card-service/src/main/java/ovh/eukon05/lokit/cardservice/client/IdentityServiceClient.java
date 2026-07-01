package ovh.eukon05.lokit.cardservice.client;

import java.util.UUID;

public interface IdentityServiceClient {
    boolean checkUserExists(UUID userId);
}
