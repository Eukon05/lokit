package ovh.eukon05.lokit.deviceservice.config;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ovh.eukon05.lokit.deviceservice.listener.MqttHeartbeatListener;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(LokitMqttProperties.class)
public class MqttConfig {
    private final MqttHeartbeatListener heartbeatListener;

    @Bean
    public IMqttAsyncClient mqttAsyncClient(LokitMqttProperties properties) throws MqttException {
        IMqttAsyncClient client = new MqttAsyncClient(properties.serverUrl(), properties.clientId());

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(properties.username());
        options.setPassword(properties.password().toCharArray());
        options.setAutomaticReconnect(true);

        client.connect(options).waitForCompletion();
        client.subscribe("lokit/devices/+/heartbeat", 1, heartbeatListener).waitForCompletion();

        return client;
    }

}
