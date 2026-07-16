package ovh.eukon05.lokit.identityservice.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import ovh.eukon05.lokit.common.proto.CheckUserExistsReply;
import ovh.eukon05.lokit.common.proto.CheckUserExistsRequest;
import ovh.eukon05.lokit.common.proto.IdentityServiceGrpc;
import ovh.eukon05.lokit.identityservice.repository.IdpUserRepository;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
class GrpcServerService extends IdentityServiceGrpc.IdentityServiceImplBase {
    private final IdpUserRepository repository;

    @Override
    public void checkUserExists(CheckUserExistsRequest request, StreamObserver<CheckUserExistsReply> responseObserver) {
        UUID userId = UUID.fromString(request.getUserId());
        CheckUserExistsReply reply = CheckUserExistsReply.newBuilder()
                .setExists(repository.existsById(userId))
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
