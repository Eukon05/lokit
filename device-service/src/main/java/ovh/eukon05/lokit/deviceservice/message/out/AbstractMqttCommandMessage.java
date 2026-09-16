package ovh.eukon05.lokit.deviceservice.message.out;

import lombok.Getter;

@Getter
public abstract class AbstractMqttCommandMessage {
    private final String command;

    AbstractMqttCommandMessage(String command) {
        this.command = command;
    }
}
