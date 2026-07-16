package ovh.eukon05.lokit.cardservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.proto.CheckUserExistsRequest;
import ovh.eukon05.lokit.common.proto.IdentityServiceGrpc;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdentityServiceClientImpl implements IdentityServiceClient {
    private final IdentityServiceGrpc.IdentityServiceBlockingStub stub;

    @Override
    public boolean checkUserExists(UUID userId) {
        CheckUserExistsRequest request = CheckUserExistsRequest.newBuilder().setUserId(userId.toString()).build();
        return stub.checkUserExists(request).getExists();
    }
}
