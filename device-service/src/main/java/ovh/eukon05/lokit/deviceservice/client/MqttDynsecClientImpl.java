package ovh.eukon05.lokit.deviceservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.deviceservice.messages.out.AbstractMqttCommand;
import ovh.eukon05.lokit.deviceservice.messages.out.CreateMqttDeviceClientMessage;
import ovh.eukon05.lokit.deviceservice.messages.out.MqttCommandsMessage;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MqttDynsecClientImpl implements MqttDynsecClient {
    private static final String DYNSEC_TOPIC = "$CONTROL/dynamic-security/v1";
    private static final int QOS = 1;

    private final IMqttAsyncClient client;
    private final ObjectMapper mapper;

    @Override
    public void createDevice(CreateMqttDeviceClientMessage message) {
        List<AbstractMqttCommand> commands = List.of(message);
        MqttCommandsMessage payload = new MqttCommandsMessage(commands);
        try {
            byte[] payloadBytes = mapper.writeValueAsBytes(payload);
            MqttMessage mqttMessage = new MqttMessage(payloadBytes);
            mqttMessage.setQos(QOS);
            client.publish(DYNSEC_TOPIC, mqttMessage).waitForCompletion();
            log.debug("Published createClient command for clientid: {}", message.getClientid());
        } catch (MqttException e) {
            throw new RuntimeException("Failed to publish createClient command for clientid: " + message.getClientid(), e);
        }
    }
}
