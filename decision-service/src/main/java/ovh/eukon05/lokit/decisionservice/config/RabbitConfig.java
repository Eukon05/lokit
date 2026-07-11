package ovh.eukon05.lokit.decisionservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ovh.eukon05.lokit.common.config.RabbitConstants.DECISION_SERVICE_QUEUE;
import static ovh.eukon05.lokit.common.config.RabbitConstants.EXCHANGE_NAME;

@Configuration
public class RabbitConfig {
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(DECISION_SERVICE_QUEUE, true);
    }

    @Bean
    public Binding bindRoleEvents(TopicExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("role.#");
    }

    @Bean
    public Binding bindRoomEvents(TopicExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("room.#");
    }

    @Bean
    public Binding bindCardEvents(TopicExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("card.#");
    }

    @Bean
    public Binding bindDeviceEvents(TopicExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("device.#");
    }

    @Bean
    public Binding bindUserEvents(TopicExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("user.#");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter("ovh.eukon05.lokit.common.event.dto");
    }
}
