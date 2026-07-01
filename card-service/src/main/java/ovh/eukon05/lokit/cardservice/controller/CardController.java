package ovh.eukon05.lokit.cardservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ovh.eukon05.lokit.cardservice.dto.request.CreateCardDTO;
import ovh.eukon05.lokit.cardservice.dto.response.GetCardDTO;
import ovh.eukon05.lokit.cardservice.facade.CardFacade;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {
    private final CardFacade cardFacade;

    @GetMapping("/{cardId}")
    public GetCardDTO findById(@PathVariable UUID cardId) {
        return cardFacade.getCard(cardId);
    }

    @PostMapping
    public UUID createCard(@RequestBody @Valid CreateCardDTO cardDTO) {
        return cardFacade.createCard(cardDTO);
    }

    @GetMapping
    public PagedModel<GetCardDTO> findAll(Pageable pageable) {
        return cardFacade.findAll(pageable);
    }

    @DeleteMapping("/{cardId}")
    public void deleteCard(@PathVariable UUID cardId) {
        cardFacade.deleteCard(cardId);
    }

    @PostMapping("/{cardId}/enable")
    public void enableCard(@PathVariable UUID cardId) {
        cardFacade.enableCard(cardId);
    }

    @PostMapping("/{cardId}/disable")
    public void disableCard(@PathVariable UUID cardId) {
        cardFacade.disableCard(cardId);
    }
}
