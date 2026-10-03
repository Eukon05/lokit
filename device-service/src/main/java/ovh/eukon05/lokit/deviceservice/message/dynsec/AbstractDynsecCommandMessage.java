package ovh.eukon05.lokit.deviceservice.message.dynsec;

import lombok.Getter;

@Getter
public abstract class AbstractDynsecCommandMessage {
    private final String command;

    AbstractDynsecCommandMessage(String command) {
        this.command = command;
    }
}
