package ovh.eukon05.lokit.decisionservice.service;

import java.util.UUID;

public interface DecisionService {
    boolean getDecision(UUID cardId, UUID deviceId);
}
