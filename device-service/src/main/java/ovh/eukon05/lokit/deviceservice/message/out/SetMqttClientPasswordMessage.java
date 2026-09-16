package ovh.eukon05.lokit.deviceservice.message.out;

import lombok.Getter;

@Getter
public class SetMqttClientPasswordMessage extends AbstractMqttCommandMessage {
    private final String username;
    private final String password;

    public SetMqttClientPasswordMessage(String username, String password) {
        super("setClientPassword");
        this.username = username;
        this.password = password;
    }
}
