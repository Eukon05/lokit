package ovh.eukon05.lokit.cardservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.cardservice.exception.CardNotFoundException;
import ovh.eukon05.lokit.cardservice.model.CardEntity;
import ovh.eukon05.lokit.cardservice.repository.CardRepository;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Override
    public CardEntity findById(String id) {
        return cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException(id));
    }

    @Override
    public Page<CardEntity> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    @Override
    public String saveCard(CardEntity card) {
        cardRepository.save(card);
        return card.getId();
    }

    @Override
    public void deleteCard(String id) {
        cardRepository.deleteById(id);
    }

    @Override
    public CardEntity enableCard(String id) {
        CardEntity card = findById(id);
        card.setActive(true);
        return cardRepository.save(card);
    }

    @Override
    public void disableCard(String id) {
        CardEntity card = findById(id);
        card.setActive(false);
        cardRepository.save(card);
    }
}
