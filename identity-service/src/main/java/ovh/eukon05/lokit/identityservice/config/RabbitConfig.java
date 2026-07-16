package ovh.eukon05.lokit.identityservice.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ovh.eukon05.lokit.common.event.RabbitEventPublisher;

import static ovh.eukon05.lokit.common.config.RabbitConstants.EXCHANGE_NAME;

@Configuration
class RabbitConfig {

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter("ovh.eukon05.lokit.common.event.dto");
    }

    @Bean
    RabbitEventPublisher rabbitEventPublisher(RabbitTemplate rabbitTemplate) {
        return new RabbitEventPublisher(rabbitTemplate::convertAndSend);
    }
}
