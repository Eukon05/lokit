package ovh.eukon05.lokit.deviceservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.deviceservice.message.device.out.DeviceCommandMessage;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceCommandClientImpl implements DeviceCommandClient {
    private static final String DEVICE_COMMAND_TOPIC = "lokit/devices/%s/command";
    private static final int QOS = 1;

    private final IMqttAsyncClient mqttClient;
    private final ObjectMapper mapper;

    @Override
    public void sendCommand(String physicalAddress, DeviceCommandMessage message) {
        try {
            byte[] payloadBytes = mapper.writeValueAsBytes(message);
            MqttMessage mqttMessage = new MqttMessage(payloadBytes);
            mqttMessage.setQos(QOS);
            mqttClient.publish(DEVICE_COMMAND_TOPIC.formatted(physicalAddress), mqttMessage).waitForCompletion();
            log.debug("Published {} command for MAC address: {}", message.command(), physicalAddress);
        } catch (MqttException e) {
            throw new RuntimeException("Failed to publish " + message.command() + " command for MAC address: " + physicalAddress, e);
        }
    }
}
