package ovh.eukon05.lokit.deviceservice.message.dynsec;

import java.util.List;

public record DynsecCommandsMessage(List<AbstractDynsecCommandMessage> commands) {
}
