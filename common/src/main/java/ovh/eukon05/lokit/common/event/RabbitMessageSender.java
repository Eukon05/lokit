package ovh.eukon05.lokit.common.event;

@FunctionalInterface
public interface RabbitMessageSender {
    void sendObject(String exchange, String routingKey, Object message);
}
