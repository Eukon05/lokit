package ovh.eukon05.lokit.cardservice.client;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdentityServiceClientImpl implements IdentityServiceClient {

    @Override
    public boolean checkUserExists(UUID userId) {
        return true; //placeholder
    }
}
