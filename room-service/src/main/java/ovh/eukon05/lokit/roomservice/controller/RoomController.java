package ovh.eukon05.lokit.roomservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ovh.eukon05.lokit.roomservice.dto.request.CreateRoomDTO;
import ovh.eukon05.lokit.roomservice.dto.response.GetRoomDTO;
import ovh.eukon05.lokit.roomservice.facade.RoomFacade;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomFacade roomFacade;

    @GetMapping("/{roomId}")
    public GetRoomDTO findById(@PathVariable UUID roomId) {
        return roomFacade.getRoom(roomId);
    }

    @PostMapping
    public UUID createRoom(@RequestBody @Valid CreateRoomDTO roomDTO) {
        return roomFacade.createRoom(roomDTO);
    }

    @GetMapping
    public PagedModel<GetRoomDTO> findAll(Pageable pageable) {
        return roomFacade.findAll(pageable);
    }

    @PostMapping("/{roomId}/acl/{roleId}")
    public void addToRoomACL(@PathVariable UUID roomId, @PathVariable UUID roleId) {
        roomFacade.addToRoomACL(roomId, roleId);
    }

    @DeleteMapping("/{roomId}/acl/{roleId}")
    public void removeFromRoomACL(@PathVariable UUID roomId, @PathVariable UUID roleId) {
        roomFacade.removeFromRoomACL(roomId, roleId);
    }
}
