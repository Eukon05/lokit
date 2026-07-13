package ovh.eukon05.lokit.deviceservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import ovh.eukon05.lokit.deviceservice.dto.request.CreateDeviceDTO;
import ovh.eukon05.lokit.deviceservice.dto.response.GetDeviceDTO;
import ovh.eukon05.lokit.deviceservice.model.DeviceEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeviceMapper {
    String HAS_TOKEN_MAPPING = "hasTokenMapping";

    @Mapping(source = "tokenHash", target = "hasActiveToken", qualifiedByName = HAS_TOKEN_MAPPING)
    GetDeviceDTO toGetDeviceDTO(DeviceEntity device);

    DeviceEntity fromCreateDeviceDTO(CreateDeviceDTO device);

    @Named(HAS_TOKEN_MAPPING)
    static boolean hasTokenMapping(String tokenHash) {
        return tokenHash != null && !tokenHash.isBlank();
    }
}
