package ovh.eukon05.lokit.deviceservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.deviceservice.service.DeviceService;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class MqttHeartbeatListener implements IMqttMessageListener {
    private static final Pattern topicPattern = Pattern.compile("lokit/devices/(.*)/heartbeat");
    private final DeviceService service;

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Matcher matcher = topicPattern.matcher(topic);
        if (matcher.matches()) {
            String deviceMac = matcher.group(1).toUpperCase();
            service.updateLastSeen(deviceMac, Instant.now());
            log.debug("Received heartbeat message from device with MAC: {}", deviceMac);
        } else {
            log.error("Invalid topic for heartbeat listener: {}", topic);
        }
    }
}
