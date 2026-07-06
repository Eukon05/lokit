package ovh.eukon05.lokit.roomservice.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import ovh.eukon05.lokit.common.proto.CheckRoomExistsReply;
import ovh.eukon05.lokit.common.proto.CheckRoomExistsRequest;
import ovh.eukon05.lokit.common.proto.GetRoomReply;
import ovh.eukon05.lokit.common.proto.RoomServiceGrpc;
import ovh.eukon05.lokit.roomservice.model.RoomEntity;
import ovh.eukon05.lokit.roomservice.repository.RoomRepository;

import java.util.List;
import java.util.UUID;

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

    @Override
    public void checkRoomExists(CheckRoomExistsRequest request, StreamObserver<CheckRoomExistsReply> responseObserver) {
        UUID roomId = UUID.fromString(request.getRoomId());
        CheckRoomExistsReply reply = CheckRoomExistsReply.newBuilder()
                .setExists(roomRepository.existsByIdAndActiveTrue(roomId))
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
