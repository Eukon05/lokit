package ovh.eukon05.lokit.deviceservice.message.out;

import lombok.Getter;

@Getter
public class EnableMqttClientMessage extends AbstractMqttCommandMessage {
    private final String username;

    public EnableMqttClientMessage(String username) {
        super("enableClient");
        this.username = username;
    }
}