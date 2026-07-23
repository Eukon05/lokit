package ovh.eukon05.lokit.cardservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "AppCard")
@Getter
@Setter
public class CardEntity {
    @Id
    @NotBlank
    @Size(min = 8, max = 8)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private UserEntity user;

    @NotBlank
    @Size(max = 100)
    private String name;
    private boolean active = true;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
