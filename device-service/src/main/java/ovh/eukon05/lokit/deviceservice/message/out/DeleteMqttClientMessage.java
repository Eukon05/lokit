package ovh.eukon05.lokit.deviceservice.message.out;

import lombok.Getter;

@Getter
public class DeleteMqttClientMessage extends AbstractMqttCommandMessage {
    private final String username;

    public DeleteMqttClientMessage(String username) {
        super("deleteClient");
        this.username = username;
    }
}
