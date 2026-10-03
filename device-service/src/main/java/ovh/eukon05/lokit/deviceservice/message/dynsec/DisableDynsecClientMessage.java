package ovh.eukon05.lokit.deviceservice.message.dynsec;

import lombok.Getter;

@Getter
public class DisableDynsecClientMessage extends AbstractDynsecCommandMessage {
    private final String username;

    public DisableDynsecClientMessage(String username) {
        super("disableClient");
        this.username = username;
    }
}
