package ovh.eukon05.lokit.roleservice.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import ovh.eukon05.lokit.common.proto.CheckRoleExistsReply;
import ovh.eukon05.lokit.common.proto.CheckRoleExistsRequest;
import ovh.eukon05.lokit.common.proto.RoleServiceGrpc;
import ovh.eukon05.lokit.roleservice.repository.RoleRepository;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class GrpcServerService extends RoleServiceGrpc.RoleServiceImplBase {
    private final RoleRepository roleRepository;

    @Override
    public void checkRoleExists(CheckRoleExistsRequest request, StreamObserver<CheckRoleExistsReply> responseObserver) {
        CheckRoleExistsReply.Builder reply = CheckRoleExistsReply.newBuilder();
        UUID roleId = UUID.fromString(request.getRoleId());

        reply.setExists(roleRepository.existsById(roleId));

        responseObserver.onNext(reply.build());
        responseObserver.onCompleted();
    }
}
