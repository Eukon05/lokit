package ovh.eukon05.lokit.cardservice.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String CARD_EXCHANGE_NAME = "lokit.events";
    public static final String CARD_CREATED_ROUTING_KEY = "card.created";
    public static final String CARD_DELETED_ROUTING_KEY = "card.deleted";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CARD_EXCHANGE_NAME);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
