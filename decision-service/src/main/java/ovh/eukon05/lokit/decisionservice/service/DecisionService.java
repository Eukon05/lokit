package ovh.eukon05.lokit.decisionservice.service;

import ovh.eukon05.lokit.decisionservice.model.DecisionStatus;

import java.util.UUID;

public interface DecisionService {
    DecisionStatus getDecision(UUID cardId, UUID deviceId);
}
