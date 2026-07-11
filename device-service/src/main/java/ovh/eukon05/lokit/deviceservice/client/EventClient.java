package ovh.eukon05.lokit.deviceservice.client;

import ovh.eukon05.lokit.common.event.dto.*;

public interface EventClient {
    void sendDeviceCreatedEvent(DeviceCreatedEventDTO dto);

    void sendDeviceDeletedEvent(DeviceDeletedEventDTO dto);

    void sendDeviceEnabledEvent(DeviceEnabledEventDTO dto);

    void sendDeviceDisabledEvent(DeviceDisabledEventDTO dto);

    void sendDeviceRoomAssignedEvent(DeviceRoomAssignedEventDTO dto);

    void sendDeviceRoomRemovedEvent(DeviceRoomRemovedEventDTO dto);
}
