package ovh.eukon05.lokit.roleservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;
import ovh.eukon05.lokit.common.proto.IdentityServiceGrpc;

@Configuration
public class GrpcConfig {
    @Bean
    IdentityServiceGrpc.IdentityServiceBlockingStub stub(GrpcChannelFactory channels) {
        return IdentityServiceGrpc.newBlockingStub(channels.createChannel("identity-service"));
    }
}
