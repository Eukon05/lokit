package ovh.eukon05.lokit.decisionservice.client;

import com.google.protobuf.Empty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.proto.GetRoomReply;
import ovh.eukon05.lokit.common.proto.RoomServiceGrpc;
import ovh.eukon05.lokit.decisionservice.model.RoomState;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceClientImpl implements RoomServiceClient {
    private final RoomServiceGrpc.RoomServiceBlockingStub stub;

    @Override
    public List<RoomState> listActiveRooms() {
        Iterator<GetRoomReply> roomIter = stub.listActiveRooms(Empty.getDefaultInstance());
        List<RoomState> rooms = new ArrayList<>();

        roomIter.forEachRemaining(item -> {
            Set<UUID> roles = item.getRoleIdsList().stream().map(UUID::fromString).collect(Collectors.toSet());
            rooms.add(new RoomState(UUID.fromString(item.getRoomId()), roles));
        });

        return rooms;
    }
}
