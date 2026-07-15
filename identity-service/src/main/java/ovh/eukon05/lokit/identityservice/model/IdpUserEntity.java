package ovh.eukon05.lokit.identityservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "AppIdpUser")
@Getter
@Setter
public class IdpUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String idpId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;
}
