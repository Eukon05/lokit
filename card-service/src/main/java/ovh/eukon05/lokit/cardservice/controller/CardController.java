package ovh.eukon05.lokit.cardservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ovh.eukon05.lokit.cardservice.dto.request.CreateCardDTO;
import ovh.eukon05.lokit.cardservice.dto.request.FindCardsByIdDTO;
import ovh.eukon05.lokit.cardservice.dto.response.GetCardDTO;
import ovh.eukon05.lokit.cardservice.facade.CardFacade;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
@PreAuthorize("hasRole('LOKIT_ADMIN')")
public class CardController {
    private final CardFacade cardFacade;

    @GetMapping("/{cardId}")
    public GetCardDTO findById(@PathVariable String cardId) {
        return cardFacade.getCard(cardId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createCard(@RequestBody @Valid CreateCardDTO cardDTO) {
        return cardFacade.createCard(cardDTO);
    }

    @GetMapping
    public PagedModel<GetCardDTO> findAll(Pageable pageable) {
        return cardFacade.findAll(pageable);
    }

    @PostMapping("/lookup")
    public List<GetCardDTO> findAllById(@RequestBody @Valid FindCardsByIdDTO dto) {
        return cardFacade.findAllById(dto);
    }

    @DeleteMapping("/{cardId}")
    public void deleteCard(@PathVariable String cardId) {
        cardFacade.deleteCard(cardId);
    }

    @PostMapping("/{cardId}/enable")
    public void enableCard(@PathVariable String cardId) {
        cardFacade.enableCard(cardId);
    }

    @PostMapping("/{cardId}/disable")
    public void disableCard(@PathVariable String cardId) {
        cardFacade.disableCard(cardId);
    }
}
