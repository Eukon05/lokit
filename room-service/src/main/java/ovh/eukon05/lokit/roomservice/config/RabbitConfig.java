package ovh.eukon05.lokit.roomservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String ROLES_EXCHANGE_NAME = "lokit.events";
    public static final String ROLE_DELETED_ROUTING_KEY = "role.deleted";
    public static final String ROOM_ENABLED_ROUTING_KEY = "room.enabled";
    public static final String ROOM_DISABLED_ROUTING_KEY = "room.disabled";
    public static final String ROOM_SERVICE_QUEUE = "lokit.roomservice.queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(ROLES_EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(ROOM_SERVICE_QUEUE, true);
    }

    @Bean
    public Binding binding(TopicExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(ROLE_DELETED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
