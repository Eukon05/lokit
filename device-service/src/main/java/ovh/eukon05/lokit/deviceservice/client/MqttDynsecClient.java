package ovh.eukon05.lokit.deviceservice.client;

import ovh.eukon05.lokit.deviceservice.messages.out.CreateMqttDeviceClientMessage;

public interface MqttDynsecClient {
    void createDevice(CreateMqttDeviceClientMessage message);
}
