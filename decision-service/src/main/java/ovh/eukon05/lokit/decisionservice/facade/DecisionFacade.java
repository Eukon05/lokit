package ovh.eukon05.lokit.decisionservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.decisionservice.dto.response.GetDecisionDTO;
import ovh.eukon05.lokit.decisionservice.model.DecisionStatus;
import ovh.eukon05.lokit.decisionservice.service.DecisionService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DecisionFacade {
    private final DecisionService decisionService;

    public GetDecisionDTO getDecision(UUID cardId, UUID deviceId) {
        DecisionStatus decision = decisionService.getDecision(cardId, deviceId);
        return new GetDecisionDTO(decision.equals(DecisionStatus.OK), Instant.now(), decision);
    }
}
