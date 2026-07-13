package ovh.eukon05.lokit.decisionservice.model;

import java.util.UUID;

public record DeviceState(UUID id, UUID roomId, String tokenHash) {
}
