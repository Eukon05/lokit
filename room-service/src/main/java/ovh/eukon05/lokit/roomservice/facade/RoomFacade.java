package ovh.eukon05.lokit.roomservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.dto.event.dto.*;
import ovh.eukon05.lokit.roomservice.client.EventClient;
import ovh.eukon05.lokit.roomservice.dto.request.CreateRoomDTO;
import ovh.eukon05.lokit.roomservice.dto.response.GetRoomDTO;
import ovh.eukon05.lokit.roomservice.mapper.RoomMapper;
import ovh.eukon05.lokit.roomservice.model.RoomEntity;
import ovh.eukon05.lokit.roomservice.service.RoomService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomFacade {
    private final RoomService roomService;
    private final RoomMapper roomMapper;
    private final EventClient eventClient;

    public GetRoomDTO getRoom(UUID id) {
        RoomEntity room = roomService.findById(id);
        return roomMapper.toGetRoomDTO(room);
    }

    public UUID createRoom(CreateRoomDTO roomDTO) {
        RoomEntity entity = roomMapper.fromCreateRoomDTO(roomDTO);
        UUID id = roomService.saveRoom(entity);
        eventClient.sendRoomCreatedEvent(new RoomCreatedEventDTO(Instant.now(), id));
        return id;
    }

    public PagedModel<GetRoomDTO> findAll(Pageable pageable) {
        return new PagedModel<>(roomService.findAll(pageable).map(roomMapper::toGetRoomDTO));
    }

    public void addToRoomACL(UUID roomId, UUID roleId) {
        roomService.addToRoomACL(roomId, roleId);
        eventClient.sendRoleAddedToACLEvent(new RoomRoleAddedEventDTO(Instant.now(), roomId, roleId));
    }

    public void removeFromRoomACL(UUID roomId, UUID roleId) {
        roomService.removeFromRoomACL(roomId, roleId);
        eventClient.sendRoleRemovedFromACLEvent(new RoomRoleRemovedEventDTO(Instant.now(), roomId, roleId));
    }

    public GetRoomDTO enableRoom(UUID id) {
        RoomEntity room = roomService.enableRoom(id);
        eventClient.sendRoomEnabledEvent(new RoomEnabledEventDTO(Instant.now(), id));
        return roomMapper.toGetRoomDTO(room);
    }

    public GetRoomDTO disableRoom(UUID id) {
        RoomEntity room = roomService.disableRoom(id);
        eventClient.sendRoomDisabledEvent(new RoomDisabledEventDTO(Instant.now(), id));
        return roomMapper.toGetRoomDTO(room);
    }
}
