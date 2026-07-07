package ovh.eukon05.lokit.decisionservice.cache;

import java.util.UUID;

public interface DecisionCache {
    boolean isDeviceActive(UUID deviceId);

    boolean isRoomActive(UUID roomId);

    boolean isCardActive(UUID cardId);

    UUID getCardUserMapping(UUID cardId);

    UUID getDeviceRoomMapping(UUID deviceId);

    boolean isEntryPermitted(UUID userId, UUID roomId);
}
