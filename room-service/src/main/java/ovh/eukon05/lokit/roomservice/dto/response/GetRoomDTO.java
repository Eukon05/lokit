package ovh.eukon05.lokit.roomservice.dto.response;

import java.util.Set;
import java.util.UUID;

public record GetRoomDTO(UUID id, String name, String description, boolean active, Set<UUID> acl) {
}
