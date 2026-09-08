package ovh.eukon05.lokit.deviceservice.messages.out;

import java.util.List;

public record MqttCommandsMessage(List<AbstractMqttCommand> commands) {
}
