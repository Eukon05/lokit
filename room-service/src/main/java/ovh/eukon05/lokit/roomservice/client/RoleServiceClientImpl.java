package ovh.eukon05.lokit.roomservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.proto.CheckRoleExistsRequest;
import ovh.eukon05.lokit.common.proto.RoleServiceGrpc;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceClientImpl implements RoleServiceClient {
    private final RoleServiceGrpc.RoleServiceBlockingStub stub;

    @Override
    public boolean checkRoleExists(UUID roleId) {
        CheckRoleExistsRequest request = CheckRoleExistsRequest.newBuilder().setRoleId(roleId.toString()).build();
        return stub.checkRoleExists(request).getExists();
    }
}
