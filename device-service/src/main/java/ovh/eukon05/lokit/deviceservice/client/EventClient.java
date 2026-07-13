package ovh.eukon05.lokit.deviceservice.client;

import ovh.eukon05.lokit.common.event.dto.*;

public interface EventClient {
    void sendDeviceCreatedEvent(DeviceCreatedEventDTO dto);

    void sendDeviceDeletedEvent(DeviceDeletedEventDTO dto);

    void sendDeviceTokenAssignedEvent(DeviceTokenAssignedEventDTO dto);

    void sendDeviceTokenRevokedEvent(DeviceTokenRevokedEventDTO dto);

    void sendDeviceRoomAssignedEvent(DeviceRoomAssignedEventDTO dto);

    void sendDeviceRoomRemovedEvent(DeviceRoomRemovedEventDTO dto);
}
