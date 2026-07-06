package ovh.eukon05.lokit.decisionservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;
import ovh.eukon05.lokit.common.proto.CardServiceGrpc;
import ovh.eukon05.lokit.common.proto.DeviceServiceGrpc;
import ovh.eukon05.lokit.common.proto.RoleServiceGrpc;
import ovh.eukon05.lokit.common.proto.RoomServiceGrpc;

@Configuration
public class GrpcConfig {
    @Bean
    public RoleServiceGrpc.RoleServiceBlockingStub roleServiceBlockingStub(GrpcChannelFactory channels) {
        return RoleServiceGrpc.newBlockingStub(channels.createChannel("role-service"));
    }

    @Bean
    public RoomServiceGrpc.RoomServiceBlockingStub roomServiceBlockingStub(GrpcChannelFactory channels) {
        return RoomServiceGrpc.newBlockingStub(channels.createChannel("room-service"));
    }

    @Bean
    public CardServiceGrpc.CardServiceBlockingStub cardServiceBlockingStub(GrpcChannelFactory channels) {
        return CardServiceGrpc.newBlockingStub(channels.createChannel("card-service"));
    }

    @Bean
    public DeviceServiceGrpc.DeviceServiceBlockingStub deviceServiceBlockingStub(GrpcChannelFactory channels) {
        return DeviceServiceGrpc.newBlockingStub(channels.createChannel("device-service"));
    }
}
