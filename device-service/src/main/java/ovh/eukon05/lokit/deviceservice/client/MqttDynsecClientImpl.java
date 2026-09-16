package ovh.eukon05.lokit.deviceservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.deviceservice.message.out.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MqttDynsecClientImpl implements MqttDynsecClient {
    private static final String DYNSEC_TOPIC = "$CONTROL/dynamic-security/v1";
    private static final int QOS = 1;

    private final IMqttAsyncClient mqttClient;
    private final ObjectMapper mapper;

    @Override
    public void createClient(String clientId, String username) {
        publish(new CreateMqttClientMessage(clientId, username), username);
    }

    @Override
    public void setClientPassword(String username, String password) {
        publish(new SetMqttClientPasswordMessage(username, password), username);
    }

    @Override
    public void enableClient(String username) {
        publish(new EnableMqttClientMessage(username), username);
    }

    @Override
    public void disableClient(String username) {
        publish(new DisableMqttClientMessage(username), username);
    }

    @Override
    public void deleteClient(String username) {
        publish(new DeleteMqttClientMessage(username), username);
    }

    private void publish(AbstractMqttCommandMessage command, String username) {
        MqttCommandsMessage payload = new MqttCommandsMessage(List.of(command));
        try {
            byte[] payloadBytes = mapper.writeValueAsBytes(payload);
            MqttMessage mqttMessage = new MqttMessage(payloadBytes);
            mqttMessage.setQos(QOS);
            mqttClient.publish(DYNSEC_TOPIC, mqttMessage).waitForCompletion();
            log.debug("Published {} command for username: {}", command.getCommand(), username);
        } catch (MqttException e) {
            throw new RuntimeException("Failed to publish " + command.getCommand() + " command for username: " + username, e);
        }
    }
}
