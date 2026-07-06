package ovh.eukon05.lokit.decisionservice.client;

import ovh.eukon05.lokit.decisionservice.model.CardState;

import java.util.List;

public interface CardServiceClient {
    List<CardState> listActiveCards();
}
