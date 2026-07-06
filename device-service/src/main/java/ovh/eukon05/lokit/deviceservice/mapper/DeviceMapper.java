package ovh.eukon05.lokit.deviceservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ovh.eukon05.lokit.deviceservice.dto.request.CreateDeviceDTO;
import ovh.eukon05.lokit.deviceservice.dto.response.GetDeviceDTO;
import ovh.eukon05.lokit.deviceservice.model.DeviceEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeviceMapper {
    GetDeviceDTO toGetDeviceDTO(DeviceEntity device);

    DeviceEntity fromCreateDeviceDTO(CreateDeviceDTO device);
}
