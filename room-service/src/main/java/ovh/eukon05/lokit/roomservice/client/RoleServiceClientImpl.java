package ovh.eukon05.lokit.roomservice.client;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleServiceClientImpl implements RoleServiceClient {

    @Override
    public boolean checkRoleExists(UUID roleId) {
        return true; //placeholder
    }
}
