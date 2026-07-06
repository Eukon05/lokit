package ovh.eukon05.lokit.decisionservice.model;

import java.util.Set;
import java.util.UUID;

public record UserState(UUID id, Set<UUID> roles) {
}
