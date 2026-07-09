package ovh.eukon05.lokit.common.dto.event;

@FunctionalInterface
public interface RabbitMessageSender {
    void sendObject(String exchange, String routingKey, Object message);
}
