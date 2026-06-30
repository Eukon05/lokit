package ovh.eukon05.lokit.roleservice.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String ROLES_EXCHANGE_NAME = "lokit.events";
    public static final String ROLES_ROUTING_KEY = "role.deleted";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(ROLES_EXCHANGE_NAME);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
