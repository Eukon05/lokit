package ovh.eukon05.lokit.deviceservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;
import ovh.eukon05.lokit.common.proto.RoomServiceGrpc;

@Configuration
public class GrpcConfig {
    @Bean
    public RoomServiceGrpc.RoomServiceBlockingStub roomServiceBlockingStub(GrpcChannelFactory channels) {
        return RoomServiceGrpc.newBlockingStub(channels.createChannel("room-service"));
    }
}
