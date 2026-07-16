package ovh.eukon05.lokit.cardservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ovh.eukon05.lokit.common.event.RabbitEventPublisher;

import static ovh.eukon05.lokit.common.config.RabbitConstants.*;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue queue() {
        return new Queue(CARD_SERVICE_QUEUE, true);
    }

    @Bean
    Binding bindUserDeletedEvent(TopicExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(USER_DELETED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter("ovh.eukon05.lokit.common.event.dto");
    }

    @Bean
    public RabbitEventPublisher rabbitEventPublisher(RabbitTemplate rabbitTemplate) {
        return new RabbitEventPublisher(rabbitTemplate::convertAndSend);
    }
}
