package ovh.eukon05.lokit.deviceservice.client;


import ovh.eukon05.lokit.deviceservice.message.device.out.DeviceCommandMessage;

public interface DeviceCommandClient {
    void sendCommand(String physicalAddress, DeviceCommandMessage command);
}
