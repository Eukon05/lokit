package ovh.eukon05.lokit.roomservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.roomservice.client.RoleServiceClient;
import ovh.eukon05.lokit.roomservice.exception.RoleNotFoundException;
import ovh.eukon05.lokit.roomservice.exception.RoomNotFoundException;
import ovh.eukon05.lokit.roomservice.model.RoomEntity;
import ovh.eukon05.lokit.roomservice.repository.RoomRepository;

import java.util.List;
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

        if (room.getAcl().contains(roleId)) return; // protects from unnecessary gRPC calls
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

    @Override
    @Transactional
    public void removeFromAllACLs(UUID roleId) {
        List<RoomEntity> rooms = roomRepository.findAllByAclContains(roleId);
        for (RoomEntity room : rooms) room.getAcl().remove(roleId);
        roomRepository.saveAll(rooms);
    }

    @Override
    public RoomEntity enableRoom(UUID id) {
        RoomEntity room = getRoom(id);
        room.setActive(true);
        return roomRepository.save(room);
    }

    @Override
    public RoomEntity disableRoom(UUID id) {
        RoomEntity room = getRoom(id);
        room.setActive(false);
        return roomRepository.save(room);
    }

    private RoomEntity getRoom(UUID id) {
        return roomRepository.findById(id).orElseThrow(RoomNotFoundException::new);
    }
}
