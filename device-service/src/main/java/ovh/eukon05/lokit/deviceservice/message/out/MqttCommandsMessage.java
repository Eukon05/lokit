package ovh.eukon05.lokit.deviceservice.message.out;

import java.util.List;

public record MqttCommandsMessage(List<AbstractMqttCommandMessage> commands) {
}
