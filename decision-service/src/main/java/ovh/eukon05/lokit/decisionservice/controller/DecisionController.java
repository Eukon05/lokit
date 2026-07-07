package ovh.eukon05.lokit.decisionservice.controller;

import lombok.RequiredArgsConstructor;
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
    public GetDecisionDTO getDecision(@RequestParam UUID cardId, @RequestParam UUID deviceId) {
        return decisionFacade.getDecision(cardId, deviceId);
    }
}
