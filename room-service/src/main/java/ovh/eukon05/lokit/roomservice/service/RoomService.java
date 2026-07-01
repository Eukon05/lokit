package ovh.eukon05.lokit.roomservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ovh.eukon05.lokit.roomservice.model.RoomEntity;

import java.util.UUID;

public interface RoomService {
    RoomEntity findById(UUID id);

    Page<RoomEntity> findAll(Pageable pageable);

    UUID saveRoom(RoomEntity room);

    void addToRoomACL(UUID roomId, UUID roleId);

    void removeFromRoomACL(UUID roomId, UUID roleId);

    void removeFromAllACLs(UUID roleId);

    RoomEntity enableRoom(UUID id);

    RoomEntity disableRoom(UUID id);
}
