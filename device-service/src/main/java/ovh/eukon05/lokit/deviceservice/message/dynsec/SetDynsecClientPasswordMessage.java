package ovh.eukon05.lokit.deviceservice.message.dynsec;

import lombok.Getter;

@Getter
public class SetDynsecClientPasswordMessage extends AbstractDynsecCommandMessage {
    private final String username;
    private final String password;

    public SetDynsecClientPasswordMessage(String username, String password) {
        super("setClientPassword");
        this.username = username;
        this.password = password;
    }
}
