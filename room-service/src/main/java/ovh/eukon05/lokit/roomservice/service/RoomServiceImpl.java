package ovh.eukon05.lokit.roomservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.roomservice.client.RoleServiceClient;
import ovh.eukon05.lokit.roomservice.exception.RoleNotFoundException;
import ovh.eukon05.lokit.roomservice.exception.RoomNotFoundException;
import ovh.eukon05.lokit.roomservice.model.RoomEntity;
import ovh.eukon05.lokit.roomservice.repository.RoomRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoleServiceClient roleServiceClient;

    @Override
    public RoomEntity findById(UUID id) {
        return getRoom(id);
    }

    @Override
    public Page<RoomEntity> findAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public UUID saveRoom(RoomEntity room) {
        roomRepository.save(room);
        return room.getId();
    }

    @Override
    public void addToRoomACL(UUID roomId, UUID roleId) {
        RoomEntity room = getRoom(roomId);

        if (!roleServiceClient.checkRoleExists(roleId)) throw new RoleNotFoundException();

        room.getAcl().add(roleId);
        roomRepository.save(room);
    }

    @Override
    public void removeFromRoomACL(UUID roomId, UUID roleId) {
        RoomEntity room = getRoom(roomId);
        room.getAcl().remove(roleId);
        roomRepository.save(room);
    }

    private RoomEntity getRoom(UUID id) {
        return roomRepository.findById(id).orElseThrow(RoomNotFoundException::new);
    }
}
