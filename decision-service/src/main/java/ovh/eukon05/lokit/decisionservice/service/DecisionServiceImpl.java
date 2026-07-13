package ovh.eukon05.lokit.decisionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.decisionservice.cache.DecisionCache;
import ovh.eukon05.lokit.decisionservice.exception.DeviceWithoutRoomException;
import ovh.eukon05.lokit.decisionservice.model.DecisionStatus;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DecisionServiceImpl implements DecisionService {
    private final DecisionCache decisionCache;

    @Override
    public DecisionStatus getDecision(UUID cardId, UUID deviceId) {
        if (!decisionCache.isCardActive(cardId))
            return DecisionStatus.CARD_DISABLED;

        UUID roomId;
        try {
            roomId = decisionCache.getDeviceRoomMapping(deviceId);
        } catch (DeviceWithoutRoomException e) {
            return DecisionStatus.DEVICE_NOT_ASSIGNED;
        }

        if (!decisionCache.isRoomActive(roomId))
            return DecisionStatus.ROOM_DISABLED;

        UUID userId = decisionCache.getCardUserMapping(cardId);
        return decisionCache.isEntryPermitted(userId, roomId) ? DecisionStatus.OK : DecisionStatus.INSUFFICIENT_ROLE;
    }
}
