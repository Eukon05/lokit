package ovh.eukon05.lokit.roleservice.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import ovh.eukon05.lokit.common.proto.*;
import ovh.eukon05.lokit.roleservice.model.RoleEntity;
import ovh.eukon05.lokit.roleservice.model.UserEntity;
import ovh.eukon05.lokit.roleservice.repository.RoleRepository;
import ovh.eukon05.lokit.roleservice.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class GrpcServerService extends RoleServiceGrpc.RoleServiceImplBase {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

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

    @Override
    public void listUserRoles(Empty request, StreamObserver<GetUserRoleReply> responseObserver) {
        List<UserEntity> users = userRepository.findAll();

        for (UserEntity user : users) {
            GetUserRoleReply reply = GetUserRoleReply.newBuilder()
                    .setUserId(user.getId().toString())
                    .addAllRoleIds(user.getRoles().stream().map(r -> r.getId().toString()).toList())
                    .build();

            responseObserver.onNext(reply);
        }

        responseObserver.onCompleted();
    }
}
