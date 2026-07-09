package ovh.eukon05.lokit.common.dto.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ovh.eukon05.lokit.common.dto.config.RabbitConstants.EXCHANGE_NAME;

public class RabbitEventPublisher {
    private final RabbitMessageSender sender;
    private final Logger logger = LoggerFactory.getLogger(RabbitEventPublisher.class);

    public RabbitEventPublisher(RabbitMessageSender sender) {
        this.sender = sender;
    }

    public void sendObject(String routingKey, Object message) {
        try {
            sender.sendObject(EXCHANGE_NAME, routingKey, message);
        } catch (Exception e) {
            logger.warn("Failed to send object to RabbitMQ: {}, Exception message: {}", message, e.getMessage());
        }
    }
}
