package ovh.eukon05.lokit.cardservice.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import ovh.eukon05.lokit.cardservice.model.CardEntity;
import ovh.eukon05.lokit.cardservice.repository.CardRepository;
import ovh.eukon05.lokit.common.proto.CardServiceGrpc;
import ovh.eukon05.lokit.common.proto.GetCardReply;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class GrpcServerService extends CardServiceGrpc.CardServiceImplBase {
    private final CardRepository cardRepository;

    @Override
    public void listActiveCards(Empty request, StreamObserver<GetCardReply> responseObserver) {
        List<CardEntity> cards = cardRepository.findAllByActiveTrue();

        for (CardEntity card : cards) {
            GetCardReply reply = GetCardReply.newBuilder()
                    .setCardId(card.getId().toString())
                    .setUserId(card.getUser().getId().toString())
                    .build();
            responseObserver.onNext(reply);
        }

        responseObserver.onCompleted();
    }
}
