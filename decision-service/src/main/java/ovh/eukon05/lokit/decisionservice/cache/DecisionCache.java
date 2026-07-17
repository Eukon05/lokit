package ovh.eukon05.lokit.decisionservice.cache;

import java.util.UUID;

public interface DecisionCache {
    boolean isRoomActive(UUID roomId);

    boolean isCardActive(String cardId);

    UUID getCardUserMapping(String cardId);

    UUID getDeviceRoomMapping(UUID deviceId);

    UUID getTokenDeviceMapping(String token);

    boolean isEntryPermitted(UUID userId, UUID roomId);

    void addActiveRole(UUID roleId);

    void removeActiveRole(UUID roleId);

    void addActiveCard(String cardId);

    void removeActiveCard(String cardId);

    void addActiveRoom(UUID roomId);

    void removeActiveRoom(UUID roomId);

    void addRoleToACL(UUID roleId, UUID roomId);

    void removeRoleFromACL(UUID roleId, UUID roomId);

    void setDeviceRoom(UUID roomId, UUID deviceId);

    void removeDeviceRoom(UUID deviceId);

    void setCardUser(String cardId, UUID userId);

    void removeCardUser(String cardId);

    void addRoleToUser(UUID roleId, UUID userId);

    void removeRoleFromUser(UUID roleId, UUID userId);

    void removeUser(UUID userId);

    void addToken(String tokenHash, UUID deviceId);

    void removeToken(UUID deviceId);
}
