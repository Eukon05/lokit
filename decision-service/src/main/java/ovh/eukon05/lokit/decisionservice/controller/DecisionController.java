package ovh.eukon05.lokit.decisionservice.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ovh.eukon05.lokit.decisionservice.dto.response.GetDecisionDTO;
import ovh.eukon05.lokit.decisionservice.facade.DecisionFacade;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/decision")
@RequiredArgsConstructor
public class DecisionController {
    private final DecisionFacade decisionFacade;

    @GetMapping
    public GetDecisionDTO getDecision(@AuthenticationPrincipal UUID deviceId, @RequestParam @NotBlank @Size(min = 8, max = 8) String cardId) {
        return decisionFacade.getDecision(cardId, deviceId);
    }

}
