package ovh.eukon05.lokit.roomservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ovh.eukon05.lokit.roomservice.dto.request.CreateRoomDTO;
import ovh.eukon05.lokit.roomservice.dto.response.GetRoomDTO;
import ovh.eukon05.lokit.roomservice.model.RoomEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {

    GetRoomDTO toGetRoomDTO(RoomEntity roomEntity);

    RoomEntity fromCreateRoomDTO(CreateRoomDTO createRoomDTO);
}
