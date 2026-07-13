package ovh.eukon05.lokit.deviceservice.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import ovh.eukon05.lokit.common.proto.DeviceServiceGrpc;
import ovh.eukon05.lokit.common.proto.GetDeviceReply;
import ovh.eukon05.lokit.deviceservice.model.DeviceEntity;
import ovh.eukon05.lokit.deviceservice.repository.DeviceRepository;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class GrpcServerService extends DeviceServiceGrpc.DeviceServiceImplBase {
    private final DeviceRepository deviceRepository;

    @Override
    public void listActiveDevices(Empty request, StreamObserver<GetDeviceReply> responseObserver) {
        List<DeviceEntity> devices = deviceRepository.findAllByTokenHashIsNotNull();

        for (DeviceEntity device : devices) {
            GetDeviceReply reply = GetDeviceReply.newBuilder()
                    .setDeviceId(device.getId().toString())
                    .setRoomId(device.getRoomId() == null ? "" : device.getRoomId().toString())
                    .setTokenHash(device.getTokenHash())
                    .build();
            responseObserver.onNext(reply);
        }

        responseObserver.onCompleted();
    }
}
