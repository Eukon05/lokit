package ovh.eukon05.lokit.deviceservice.message.out;

import lombok.Getter;

@Getter
public class DisableMqttClientMessage extends AbstractMqttCommandMessage {
    private final String username;

    public DisableMqttClientMessage(String username) {
        super("disableClient");
        this.username = username;
    }
}
