package ovh.eukon05.lokit.cardservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.cardservice.dto.request.CreateCardDTO;
import ovh.eukon05.lokit.cardservice.dto.response.GetCardDTO;
import ovh.eukon05.lokit.cardservice.mapper.CardMapper;
import ovh.eukon05.lokit.cardservice.model.CardEntity;
import ovh.eukon05.lokit.cardservice.model.UserEntity;
import ovh.eukon05.lokit.cardservice.service.CardService;
import ovh.eukon05.lokit.cardservice.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardFacade {
    private final CardService cardService;
    private final UserService userService;
    private final CardMapper cardMapper;

    public GetCardDTO getCard(UUID id) {
        return cardMapper.toGetCardDTO(cardService.findById(id));
    }

    public UUID createCard(CreateCardDTO cardDTO) {
        UserEntity user = userService.getUser(cardDTO.userId());
        CardEntity card = cardMapper.fromCreateCardDTO(cardDTO);
        card.setUser(user);

        return cardService.saveCard(card);
    }

    public void deleteCard(UUID id) {
        cardService.deleteCard(id);
    }

    public PagedModel<GetCardDTO> findAll(Pageable pageable) {
        return new PagedModel<>(cardService.findAll(pageable).map(cardMapper::toGetCardDTO));
    }
}
