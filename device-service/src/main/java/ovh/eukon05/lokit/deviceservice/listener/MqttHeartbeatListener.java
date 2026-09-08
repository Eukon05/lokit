package ovh.eukon05.lokit.deviceservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.deviceservice.messages.in.DeviceHeartbeatMessage;
import ovh.eukon05.lokit.deviceservice.service.DeviceService;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class MqttHeartbeatListener implements IMqttMessageListener {
    private final ObjectMapper mapper;
    private final DeviceService service;

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        DeviceHeartbeatMessage dto = mapper.readValue(message.getPayload(), DeviceHeartbeatMessage.class);
        service.updateLastSeen(dto.physicalAddress(), dto.timestamp());
        log.debug("Received heartbeat message from device with MAC: {}", dto.physicalAddress());
    }
}
