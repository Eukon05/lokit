package ovh.eukon05.lokit.decisionservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ovh.eukon05.lokit.common.event.dto.*;
import ovh.eukon05.lokit.decisionservice.cache.DecisionCache;

import static ovh.eukon05.lokit.common.config.RabbitConstants.DECISION_SERVICE_QUEUE;

@Component
@RequiredArgsConstructor
@RabbitListener(queues = DECISION_SERVICE_QUEUE)
@Slf4j
public class EventListener {
    private final DecisionCache decisionCache;

    @RabbitHandler
    public void receiveRoleCreatedEvent(RoleCreatedEventDTO dto) {
        log.debug("Received role created event. Adding active role {}", dto.roleId());
        decisionCache.addActiveRole(dto.roleId());
    }

    @RabbitHandler
    public void receiveRoleDeletedEvent(RoleDeletedEventDTO dto) {
        log.debug("Received role deleted event. Removing active role {}", dto.roleId());
        decisionCache.removeActiveRole(dto.roleId());
    }

    @RabbitHandler
    public void receiveRoleEnabledEvent(RoleEnabledEventDTO dto) {
        log.debug("Received role enabled event. Adding active role {}", dto.roleId());
        decisionCache.addActiveRole(dto.roleId());
    }

    @RabbitHandler
    public void receiveRoleDisabledEvent(RoleDisabledEventDTO dto) {
        log.debug("Received role disabled event. Removing active role {}", dto.roleId());
        decisionCache.removeActiveRole(dto.roleId());
    }

    @RabbitHandler
    public void receiveRoomCreatedEvent(RoomCreatedEventDTO dto) {
        log.debug("Received room created event. Adding active room {}", dto.roomId());
        decisionCache.addActiveRoom(dto.roomId());
    }

    @RabbitHandler
    public void receiveRoomEnabledEvent(RoomEnabledEventDTO dto) {
        log.debug("Received room enabled event. Adding active room {}", dto.roomId());
        decisionCache.addActiveRoom(dto.roomId());
    }

    @RabbitHandler
    public void receiveRoomDisabledEvent(RoomDisabledEventDTO dto) {
        log.debug("Received room disabled event. Removing active room {}", dto.roomId());
        decisionCache.removeActiveRoom(dto.roomId());
    }

    @RabbitHandler
    public void receiveRoomRoleAddedEvent(RoomRoleAddedEventDTO dto) {
        log.debug("Received room role added event. Adding role {} to room {} ACL", dto.roleId(), dto.roomId());
        decisionCache.addRoleToACL(dto.roleId(), dto.roomId());
    }

    @RabbitHandler
    public void receiveRoomRoleRemovedEvent(RoomRoleRemovedEventDTO dto) {
        log.debug("Received room role removed event. Removing role {} from room {} ACL", dto.roleId(), dto.roomId());
        decisionCache.removeRoleFromACL(dto.roleId(), dto.roomId());
    }

    @RabbitHandler
    public void receiveCardCreatedEvent(CardCreatedEventDTO dto) {
        log.debug("Received card created event. Adding active card {} mapped to user {}", dto.cardId(), dto.userId());
        decisionCache.addActiveCard(dto.cardId());
        decisionCache.setCardUser(dto.cardId(), dto.userId());
    }

    @RabbitHandler
    public void receiveCardDeletedEvent(CardDeletedEventDTO dto) {
        log.debug("Received card deleted event. Removing active card {} and its user mapping", dto.cardId());
        decisionCache.removeActiveCard(dto.cardId());
        decisionCache.removeCardUser(dto.cardId());
    }

    @RabbitHandler
    public void receiveCardEnabledEvent(CardEnabledEventDTO dto) {
        log.debug("Received card enabled event. Adding active card {} mapped to user {}", dto.cardId(), dto.userId());
        decisionCache.addActiveCard(dto.cardId());
        decisionCache.setCardUser(dto.cardId(), dto.userId());
    }

    @RabbitHandler
    public void receiveCardDisabledEvent(CardDisabledEventDTO dto) {
        log.debug("Received card disabled event. Removing active card {}", dto.cardId());
        decisionCache.removeActiveCard(dto.cardId());
    }

    @RabbitHandler
    public void receiveDeviceDeletedEvent(DeviceDeletedEventDTO dto) {
        log.debug("Received device deleted event. Removing device {} room mapping and token", dto.deviceId());
        decisionCache.removeDeviceRoom(dto.deviceId());
        decisionCache.removeToken(dto.deviceId());
    }

    @RabbitHandler
    public void receiveDeviceTokenAssignedEvent(DeviceTokenAssignedEventDTO dto) {
        log.debug("Received device token assigned event. Adding token hash {} mapped to device {}", dto.tokenHash(), dto.deviceId());
        decisionCache.addToken(dto.tokenHash(), dto.deviceId());
    }

    @RabbitHandler
    public void receiveDeviceTokenRevokedEvent(DeviceTokenRevokedEventDTO dto) {
        log.debug("Received device token revoked event. Removing token mapped to device {}", dto.deviceId());
        decisionCache.removeToken(dto.deviceId());
    }

    @RabbitHandler
    public void receiveDeviceRoomAssignedEvent(DeviceRoomAssignedEventDTO dto) {
        log.debug("Received device room assigned event. Mapping device {} to room {}", dto.deviceId(), dto.roomId());
        decisionCache.setDeviceRoom(dto.roomId(), dto.deviceId());
    }

    @RabbitHandler
    public void receiveDeviceRoomRemovedEvent(DeviceRoomRemovedEventDTO dto) {
        log.debug("Received device room removed event. Removing device {} room mapping", dto.deviceId());
        decisionCache.removeDeviceRoom(dto.deviceId());
    }

    @RabbitHandler
    public void receiveUserRoleAddedEvent(UserRoleAddedEventDTO dto) {
        log.debug("Received user role added event. Adding role {} to user {}", dto.roleId(), dto.userId());
        decisionCache.addRoleToUser(dto.roleId(), dto.userId());
    }

    @RabbitHandler
    public void receiveUserRoleRemovedEvent(UserRoleRemovedEventDTO dto) {
        log.debug("Received user role removed event. Removing role {} from user {}", dto.roleId(), dto.userId());
        decisionCache.removeRoleFromUser(dto.roleId(), dto.userId());
    }

    @RabbitHandler
    public void receiveUserDeletedEvent(UserDeletedEventDTO dto) {
        log.debug("Received user deleted event. Removing user {} roles and cards", dto.userId());
        decisionCache.removeUser(dto.userId());
    }

}
