package ovh.eukon05.lokit.decisionservice.client;

import com.google.protobuf.Empty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.proto.GetRoleReply;
import ovh.eukon05.lokit.common.proto.GetUserRoleReply;
import ovh.eukon05.lokit.common.proto.RoleServiceGrpc;
import ovh.eukon05.lokit.decisionservice.model.UserState;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceClientImpl implements RoleServiceClient {
    private final RoleServiceGrpc.RoleServiceBlockingStub stub;

    @Override
    public List<UUID> listActiveRoles() {
        Iterator<GetRoleReply> roleIter = stub.listActiveRoles(Empty.getDefaultInstance());
        List<UUID> roles = new ArrayList<>();

        roleIter.forEachRemaining(item -> roles.add(UUID.fromString(item.getRoleId())));
        return roles;
    }

    @Override
    public List<UserState> listUserRoles() {
        Iterator<GetUserRoleReply> userIter = stub.listUserRoles(Empty.getDefaultInstance());
        List<UserState> userStates = new ArrayList<>();

        userIter.forEachRemaining(item -> {
            Set<UUID> roles = item.getRoleIdsList().stream().map(UUID::fromString).collect(Collectors.toSet());
            userStates.add(new UserState(UUID.fromString(item.getUserId()), roles));
        });

        return userStates;
    }
}
