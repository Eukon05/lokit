package ovh.eukon05.lokit.cardservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.cardservice.client.EventClient;
import ovh.eukon05.lokit.cardservice.dto.request.CreateCardDTO;
import ovh.eukon05.lokit.cardservice.dto.response.GetCardDTO;
import ovh.eukon05.lokit.cardservice.mapper.CardMapper;
import ovh.eukon05.lokit.cardservice.model.CardEntity;
import ovh.eukon05.lokit.cardservice.model.UserEntity;
import ovh.eukon05.lokit.cardservice.service.CardService;
import ovh.eukon05.lokit.cardservice.service.UserService;
import ovh.eukon05.lokit.common.dto.event.dto.CardCreatedEventDTO;
import ovh.eukon05.lokit.common.dto.event.dto.CardDeletedEventDTO;
import ovh.eukon05.lokit.common.dto.event.dto.CardDisabledEventDTO;
import ovh.eukon05.lokit.common.dto.event.dto.CardEnabledEventDTO;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardFacade {
    private final CardService cardService;
    private final UserService userService;
    private final EventClient eventClient;
    private final CardMapper cardMapper;

    public GetCardDTO getCard(UUID id) {
        return cardMapper.toGetCardDTO(cardService.findById(id));
    }

    public UUID createCard(CreateCardDTO cardDTO) {
        UserEntity user = userService.getUser(cardDTO.userId());
        CardEntity card = cardMapper.fromCreateCardDTO(cardDTO);
        card.setUser(user);
        UUID id = cardService.saveCard(card);
        eventClient.sendCardCreatedEvent(new CardCreatedEventDTO(Instant.now(), id, cardDTO.userId()));
        return id;
    }

    public void deleteCard(UUID id) {
        cardService.deleteCard(id);
        eventClient.sendCardDeletedEvent(new CardDeletedEventDTO(Instant.now(), id));
    }

    public void enableCard(UUID id) {
        CardEntity card = cardService.enableCard(id);
        eventClient.sendCardEnabledEvent(new CardEnabledEventDTO(Instant.now(), id, card.getUser().getId()));
    }

    public void disableCard(UUID id) {
        cardService.disableCard(id);
        eventClient.sendCardDisabledEvent(new CardDisabledEventDTO(Instant.now(), id));
    }

    public PagedModel<GetCardDTO> findAll(Pageable pageable) {
        return new PagedModel<>(cardService.findAll(pageable).map(cardMapper::toGetCardDTO));
    }
}
