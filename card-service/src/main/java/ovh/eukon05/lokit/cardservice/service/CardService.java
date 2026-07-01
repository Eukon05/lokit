package ovh.eukon05.lokit.cardservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ovh.eukon05.lokit.cardservice.model.CardEntity;

import java.util.UUID;

public interface CardService {
    CardEntity findById(UUID id);

    Page<CardEntity> findAll(Pageable pageable);

    UUID saveCard(CardEntity card);

    void deleteCard(UUID id);
}
