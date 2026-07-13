package ovh.eukon05.lokit.deviceservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "AppDevice")
@Getter
@Setter
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String physicalAddress;

    private UUID roomId;
    private String tokenHash;
}
