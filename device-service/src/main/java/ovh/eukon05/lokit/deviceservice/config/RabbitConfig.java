package ovh.eukon05.lokit.deviceservice.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String DEVICE_EXCHANGE_NAME = "lokit.events";
    public static final String DEVICE_CREATED_ROUTING_KEY = "device.created";
    public static final String DEVICE_DELETED_ROUTING_KEY = "device.deleted";
    public static final String DEVICE_ENABLED_ROUTING_KEY = "device.enabled";
    public static final String DEVICE_DISABLED_ROUTING_KEY = "device.disabled";
    public static final String DEVICE_ROOM_ASSIGNED_ROUTING_KEY = "device.room.assigned";
    public static final String DEVICE_ROOM_REMOVED_ROUTING_KEY = "device.room.removed";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(DEVICE_EXCHANGE_NAME);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
