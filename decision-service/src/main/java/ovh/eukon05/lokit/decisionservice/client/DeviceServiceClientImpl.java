package ovh.eukon05.lokit.decisionservice.client;

import com.google.protobuf.Empty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.proto.DeviceServiceGrpc;
import ovh.eukon05.lokit.common.proto.GetDeviceReply;
import ovh.eukon05.lokit.decisionservice.model.DeviceState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceServiceClientImpl implements DeviceServiceClient {
    private final DeviceServiceGrpc.DeviceServiceBlockingStub stub;

    @Override
    public List<DeviceState> listActiveDevices() {
        Iterator<GetDeviceReply> deviceIter = stub.listActiveDevices(Empty.getDefaultInstance());
        List<DeviceState> devices = new ArrayList<>();

        deviceIter.forEachRemaining(item -> devices.add(new DeviceState(UUID.fromString(item.getDeviceId()), UUID.fromString(item.getRoomId()))));
        return devices;
    }
}
