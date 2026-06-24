package ovh.eukon05.lokit.roomservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.roomservice.dto.request.CreateRoomDTO;
import ovh.eukon05.lokit.roomservice.dto.response.GetRoomDTO;
import ovh.eukon05.lokit.roomservice.mapper.RoomMapper;
import ovh.eukon05.lokit.roomservice.model.RoomEntity;
import ovh.eukon05.lokit.roomservice.service.RoomService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomFacade {
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    public GetRoomDTO getRoom(UUID id) {
        RoomEntity room = roomService.findById(id);
        return roomMapper.toGetRoomDTO(room);
    }

    public UUID createRoom(CreateRoomDTO roomDTO) {
        RoomEntity entity = roomMapper.fromCreateRoomDTO(roomDTO);
        return roomService.saveRoom(entity);
    }

    public PagedModel<GetRoomDTO> findAll(Pageable pageable) {
        return new PagedModel<>(roomService.findAll(pageable).map(roomMapper::toGetRoomDTO));
    }

    public void addToRoomACL(UUID roomId, UUID roleId) {
        roomService.addToRoomACL(roomId, roleId);
    }

    public void removeFromRoomACL(UUID roomId, UUID roleId) {
        roomService.removeFromRoomACL(roomId, roleId);
    }
}
