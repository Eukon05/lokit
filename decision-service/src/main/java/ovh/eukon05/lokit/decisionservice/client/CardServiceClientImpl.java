package ovh.eukon05.lokit.decisionservice.client;

import com.google.protobuf.Empty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.eukon05.lokit.common.proto.CardServiceGrpc;
import ovh.eukon05.lokit.common.proto.GetCardReply;
import ovh.eukon05.lokit.decisionservice.model.CardState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceClientImpl implements CardServiceClient {
    private final CardServiceGrpc.CardServiceBlockingStub stub;

    @Override
    public List<CardState> listActiveCards() {
        Iterator<GetCardReply> cardsIter = stub.listActiveCards(Empty.getDefaultInstance());
        List<CardState> cards = new ArrayList<>();

        cardsIter.forEachRemaining(item -> cards.add(new CardState(item.getCardId(), UUID.fromString(item.getUserId()))));
        return cards;
    }
}
