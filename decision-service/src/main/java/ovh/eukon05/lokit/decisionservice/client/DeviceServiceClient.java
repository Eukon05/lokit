package ovh.eukon05.lokit.decisionservice.client;

import ovh.eukon05.lokit.decisionservice.model.DeviceState;

import java.util.List;

public interface DeviceServiceClient {
    List<DeviceState> listActiveDevices();
}
