package ovh.eukon05.lokit.deviceservice.mapper;

import org.mapstruct.*;
import ovh.eukon05.lokit.deviceservice.dto.request.SendDeviceCommandDTO;
import ovh.eukon05.lokit.deviceservice.dto.response.GetDeviceCommandDTO;
import ovh.eukon05.lokit.deviceservice.model.DeviceCommandEntity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeviceCommandMapper {

    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(source = "command", target = "type")
    GetDeviceCommandDTO toGetDeviceCommandDTO(DeviceCommandEntity command);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "issuedAt", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    @Mapping(target = "status", constant = "SENT")
    DeviceCommandEntity fromSendDeviceCommandDTO(SendDeviceCommandDTO dto);

    @AfterMapping
    default void setTimestamps(@MappingTarget DeviceCommandEntity command) {
        Instant now = Instant.now();
        command.setIssuedAt(now);
        command.setExpiresAt(now.plus(5, ChronoUnit.MINUTES));
    }
}
