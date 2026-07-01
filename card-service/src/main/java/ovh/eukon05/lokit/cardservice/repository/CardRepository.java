package ovh.eukon05.lokit.cardservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.eukon05.lokit.cardservice.model.CardEntity;

import java.util.UUID;

public interface CardRepository extends JpaRepository<CardEntity, UUID> {

}
