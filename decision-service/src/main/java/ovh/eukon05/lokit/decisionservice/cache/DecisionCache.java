package ovh.eukon05.lokit.decisionservice.cache;

import java.util.UUID;

public interface DecisionCache {
    boolean isDeviceActive(UUID deviceId);

    boolean isRoomActive(UUID roomId);

    boolean isCardActive(UUID cardId);

    UUID getCardUserMapping(UUID cardId);

    UUID getDeviceRoomMapping(UUID deviceId);

    boolean isEntryPermitted(UUID userId, UUID roomId);

    void addActiveRole(UUID roleId);

    void removeActiveRole(UUID roleId);

    void addActiveCard(UUID cardId);

    void removeActiveCard(UUID cardId);

    void addActiveRoom(UUID roomId);

    void removeActiveRoom(UUID roomId);

    void addActiveDevice(UUID deviceId);

    void removeActiveDevice(UUID deviceId);

    void addRoleToACL(UUID roleId, UUID roomId);

    void removeRoleFromACL(UUID roleId, UUID roomId);

    void setDeviceRoom(UUID roomId, UUID deviceId);

    void removeDeviceRoom(UUID deviceId);

    void setCardUser(UUID cardId, UUID userId);

    void removeCardUser(UUID cardId);

    void addRoleToUser(UUID roleId, UUID userId);

    void removeRoleFromUser(UUID roleId, UUID userId);
}
