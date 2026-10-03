package ovh.eukon05.lokit.deviceservice.message.dynsec;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateDynsecClientMessage extends AbstractDynsecCommandMessage {
    private final List<DynsecRoleMessage> roles = List.of(new DynsecRoleMessage("lokit-device", -1));

    private final String clientid;
    private final String username;

    public CreateDynsecClientMessage(String clientid, String username) {
        super("createClient");
        this.clientid = clientid;
        this.username = username;
    }
}
