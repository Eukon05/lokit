package ovh.eukon05.lokit.deviceservice.message.out;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateMqttClientMessage extends AbstractMqttCommandMessage {
    private final List<MqttRoleMessage> roles = List.of(new MqttRoleMessage("lokit-device", -1));

    private final String clientid;
    private final String username;

    public CreateMqttClientMessage(String clientid, String username) {
        super("createClient");
        this.clientid = clientid;
        this.username = username;
    }
}
