package ovh.eukon05.lokit.roomservice.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import ovh.eukon05.lokit.common.proto.GetRoomReply;
import ovh.eukon05.lokit.common.proto.RoomServiceGrpc;
import ovh.eukon05.lokit.roomservice.model.RoomEntity;
import ovh.eukon05.lokit.roomservice.repository.RoomRepository;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class GrpcServerService extends RoomServiceGrpc.RoomServiceImplBase {
    private final RoomRepository roomRepository;

    @Override
    public void listActiveRooms(Empty request, StreamObserver<GetRoomReply> responseObserver) {
        List<RoomEntity> rooms = roomRepository.findAllByActiveTrue();

        for (RoomEntity room : rooms) {
            GetRoomReply reply = GetRoomReply.newBuilder()
                    .setRoomId(room.getId().toString())
                    .addAllRoleIds(room.getAcl().stream().map(String::valueOf).toList())
                    .build();
            responseObserver.onNext(reply);
        }

        responseObserver.onCompleted();
    }
}
