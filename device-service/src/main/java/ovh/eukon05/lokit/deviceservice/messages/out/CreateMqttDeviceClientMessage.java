package ovh.eukon05.lokit.deviceservice.messages.out;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateMqttDeviceClientMessage extends AbstractMqttCommand {
    private final List<MqttRoleMessage> roles = List.of(new MqttRoleMessage("lokit-device", -1));

    private final String clientid;
    private final String username;
    private final String password;

    public CreateMqttDeviceClientMessage(String clientid, String username, String password) {
        super("createClient");
        this.clientid = clientid;
        this.username = username;
        this.password = password;
    }
}
