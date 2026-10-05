package ovh.eukon05.lokit.deviceservice.message.dynsec;

import lombok.Getter;

@Getter
public class DeleteDynsecClientMessage extends AbstractDynsecCommandMessage {
    private final String username;

    public DeleteDynsecClientMessage(String username) {
        super("deleteClient");
        this.username = username;
    }
}
