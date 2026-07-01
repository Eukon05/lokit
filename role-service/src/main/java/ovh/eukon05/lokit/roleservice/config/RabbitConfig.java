package ovh.eukon05.lokit.roleservice.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String ROLES_EXCHANGE_NAME = "lokit.events";
    public static final String ROLE_DELETED_ROUTING_KEY = "role.deleted";
    public static final String ROLE_ENABLED_ROUTING_KEY = "role.enabled";
    public static final String ROLE_DISABLED_ROUTING_KEY = "role.disabled";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(ROLES_EXCHANGE_NAME);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
