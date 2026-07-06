package ovh.eukon05.lokit.decisionservice.dto.response;

import ovh.eukon05.lokit.decisionservice.model.DecisionStatus;

import java.time.Instant;

public record GetDecisionDTO(boolean decision, Instant timestamp, DecisionStatus status) {
}
