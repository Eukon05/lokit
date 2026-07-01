package ovh.eukon05.lokit.roleservice.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import ovh.eukon05.lokit.common.proto.CheckRoleExistsReply;
import ovh.eukon05.lokit.common.proto.CheckRoleExistsRequest;
import ovh.eukon05.lokit.common.proto.GetRoleReply;
import ovh.eukon05.lokit.common.proto.RoleServiceGrpc;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;
import ovh.eukon05.lokit.roleservice.repository.RoleRepository;

import java.util.List;
import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class GrpcServerService extends RoleServiceGrpc.RoleServiceImplBase {
    private final RoleRepository roleRepository;

    @Override
    public void checkRoleExists(CheckRoleExistsRequest request, StreamObserver<CheckRoleExistsReply> responseObserver) {
        CheckRoleExistsReply.Builder reply = CheckRoleExistsReply.newBuilder();
        UUID roleId = UUID.fromString(request.getRoleId());

        reply.setExists(roleRepository.existsByIdAndActiveTrue(roleId));

        responseObserver.onNext(reply.build());
        responseObserver.onCompleted();
    }

    @Override
    public void listActiveRoles(Empty request, StreamObserver<GetRoleReply> responseObserver) {
        List<RoleEntity> roles = roleRepository.findAllByActiveTrue();

        for (RoleEntity role : roles) {
            GetRoleReply reply = GetRoleReply.newBuilder()
                    .setRoleId(role.getId().toString())
                    .build();
            responseObserver.onNext(reply);
        }

        responseObserver.onCompleted();
    }
}
