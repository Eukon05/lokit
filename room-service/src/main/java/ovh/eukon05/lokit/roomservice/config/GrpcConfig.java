package ovh.eukon05.lokit.roomservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;
import ovh.eukon05.lokit.common.proto.RoleServiceGrpc;

@Configuration
public class GrpcConfig {
    @Bean
    RoleServiceGrpc.RoleServiceBlockingStub stub(GrpcChannelFactory channels) {
        return RoleServiceGrpc.newBlockingStub(channels.createChannel("role-service"));
    }
}
