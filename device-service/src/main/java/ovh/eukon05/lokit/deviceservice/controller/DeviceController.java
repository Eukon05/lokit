package ovh.eukon05.lokit.deviceservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ovh.eukon05.lokit.deviceservice.dto.request.CreateDeviceDTO;
import ovh.eukon05.lokit.deviceservice.dto.response.GetDeviceDTO;
import ovh.eukon05.lokit.deviceservice.facade.DeviceFacade;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/device")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceFacade deviceFacade;

    @GetMapping("/{deviceId}")
    public GetDeviceDTO findById(@PathVariable UUID deviceId) {
        return deviceFacade.getDevice(deviceId);
    }

    @PostMapping
    public UUID createDevice(@RequestBody @Valid CreateDeviceDTO deviceDTO) {
        return deviceFacade.createDevice(deviceDTO);
    }

    @GetMapping
    public PagedModel<GetDeviceDTO> findAll(Pageable pageable) {
        return deviceFacade.findAll(pageable);
    }

    @DeleteMapping("/{deviceId}")
    public void deleteDevice(@PathVariable UUID deviceId) {
        deviceFacade.deleteDevice(deviceId);
    }

    @PostMapping("/{deviceId}/room/{roomId}")
    public GetDeviceDTO assignRoom(@PathVariable UUID deviceId, @PathVariable UUID roomId) {
        return deviceFacade.assignRoom(deviceId, roomId);
    }

    @DeleteMapping("/{deviceId}/room")
    public GetDeviceDTO removeRoom(@PathVariable UUID deviceId) {
        return deviceFacade.removeRoom(deviceId);
    }

    @PostMapping("/{deviceId}/token")
    public String assignToken(@PathVariable UUID deviceId) {
        return deviceFacade.assignToken(deviceId);
    }

    @DeleteMapping("/{deviceId}/token")
    public void revokeToken(@PathVariable UUID deviceId) {
        deviceFacade.revokeToken(deviceId);
    }
}
