package ovh.eukon05.lokit.deviceservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "LOKIT_DEVICE_COMMAND")
@Getter
@Setter
public class DeviceCommandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private DeviceCommandType command;

    @Enumerated(EnumType.STRING)
    private DeviceCommandStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeviceEntity device;

    private Instant issuedAt;
    private Instant expiresAt;
}
