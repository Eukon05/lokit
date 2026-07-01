package ovh.eukon05.lokit.cardservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "AppCard")
@Getter
@Setter
public class CardEntity {
    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private UserEntity user;

    private String name;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
