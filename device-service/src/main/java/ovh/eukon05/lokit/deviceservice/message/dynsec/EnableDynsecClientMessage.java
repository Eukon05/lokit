package ovh.eukon05.lokit.deviceservice.message.dynsec;

import lombok.Getter;

@Getter
public class EnableDynsecClientMessage extends AbstractDynsecCommandMessage {
    private final String username;

    public EnableDynsecClientMessage(String username) {
        super("enableClient");
        this.username = username;
    }
}