package ovh.eukon05.lokit.deviceservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotBlank
    @Size(min = 17, max = 17)
    private String physicalAddress;

    private UUID roomId;

    @Size(max = 64)
    private String tokenHash;
}
