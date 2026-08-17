package ovh.eukon05.lokit.cardservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ovh.eukon05.lokit.cardservice.model.CardEntity;

import java.util.List;
import java.util.Set;

public interface CardService {
    CardEntity findById(String id);

    Page<CardEntity> findAll(Pageable pageable);

    List<CardEntity> findAllById(Set<String> ids);

    String saveCard(CardEntity card);

    void deleteCard(String id);

    CardEntity enableCard(String id);

    void disableCard(String id);
}
