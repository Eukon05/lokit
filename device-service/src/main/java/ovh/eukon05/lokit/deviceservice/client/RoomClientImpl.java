package ovh.eukon05.lokit.deviceservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.proto.CheckRoomExistsReply;
import ovh.eukon05.lokit.common.proto.CheckRoomExistsRequest;
import ovh.eukon05.lokit.common.proto.RoomServiceGrpc;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomClientImpl implements RoomClient {
    private final RoomServiceGrpc.RoomServiceBlockingStub stub;

    @Override
    public boolean checkRoomExists(UUID roomId) {
        CheckRoomExistsRequest request = CheckRoomExistsRequest.newBuilder()
                .setRoomId(roomId.toString())
                .build();

        CheckRoomExistsReply reply = stub.checkRoomExists(request);
        return reply.getExists();
    }
}
