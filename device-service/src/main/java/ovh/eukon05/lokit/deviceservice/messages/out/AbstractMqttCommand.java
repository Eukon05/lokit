package ovh.eukon05.lokit.deviceservice.messages.out;

import lombok.Getter;

@Getter
public abstract class AbstractMqttCommand {
    private final String command;

    AbstractMqttCommand(String command) {
        this.command = command;
    }
}
