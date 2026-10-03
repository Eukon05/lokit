package ovh.eukon05.lokit.deviceservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.deviceservice.message.device.in.DeviceResponseMessage;
import ovh.eukon05.lokit.deviceservice.model.DeviceCommandStatus;
import ovh.eukon05.lokit.deviceservice.service.DeviceCommandService;
import tools.jackson.databind.ObjectMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class MqttDeviceResponseListener implements IMqttMessageListener {
    private static final Pattern topicPattern = Pattern.compile("lokit/devices/(.*)/response");
    private final DeviceCommandService service;
    private final ObjectMapper objectMapper;

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Matcher matcher = topicPattern.matcher(topic);
        if (matcher.matches()) {
            DeviceResponseMessage responseMessage = objectMapper.readValue(message.getPayload(), DeviceResponseMessage.class);

            DeviceCommandStatus newStatus = switch (responseMessage.response()) {
                case ACK -> DeviceCommandStatus.RECEIVED_ACK;
                case EXPIRED -> DeviceCommandStatus.RECEIVED_EXPIRED;
            };

            service.updateCommandStatus(responseMessage.commandId(), newStatus);
            log.debug("Received response message for command {}: {}", responseMessage.commandId(), responseMessage.response());
        } else {
            log.error("Invalid topic for response listener: {}", topic);
        }
    }
}
