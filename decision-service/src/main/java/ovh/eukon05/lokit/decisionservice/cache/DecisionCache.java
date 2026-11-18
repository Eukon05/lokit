package ovh.eukon05.lokit.decisionservice.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface DecisionCache {
    boolean isRoomActive(UUID roomId);

    boolean isCardActive(String cardId);

    UUID getCardUserMapping(String cardId);

    UUID getDeviceRoomMapping(UUID deviceId);

    UUID getTokenDeviceMapping(String token);

    boolean isEntryPermitted(UUID userId, UUID roomId);

    void addActiveRole(UUID roleId);

    void addActiveRoles(Collection<UUID> roleIds);

    void removeActiveRole(UUID roleId);

    void removeRole(UUID roleId);

    void addActiveCard(String cardId);

    void addActiveCards(Collection<String> cardIds);

    void removeActiveCard(String cardId);

    void addActiveRoom(UUID roomId);

    void addActiveRooms(Collection<UUID> roomIds);

    void removeActiveRoom(UUID roomId);

    void removeRoom(UUID roomId);

    void addRoleToACL(UUID roleId, UUID roomId);

    void addRolesToRooms(Map<UUID, Set<UUID>> roomRoles);

    void removeRoleFromACL(UUID roleId, UUID roomId);

    void setDeviceRoom(UUID roomId, UUID deviceId);

    void setDeviceRooms(Map<UUID, UUID> deviceRooms);

    void removeDeviceRoom(UUID deviceId);

    void setCardUser(String cardId, UUID userId);

    void setCardUsers(Map<String, UUID> cardUsers);

    void removeCardUser(String cardId);

    void addRoleToUser(UUID roleId, UUID userId);

    void addRolesToUsers(Map<UUID, Set<UUID>> userRoles);

    void removeRoleFromUser(UUID roleId, UUID userId);

    void removeUser(UUID userId);

    void addToken(String tokenHash, UUID deviceId);

    void addTokens(Map<UUID, String> deviceTokens);

    void removeToken(UUID deviceId);
}
