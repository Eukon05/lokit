package ovh.eukon05.lokit.cardservice.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.grpc.server.security.AuthenticationProcessInterceptor;
import org.springframework.grpc.server.security.GrpcSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ovh.eukon05.lokit.commonsecurity.LokitJwtProperties;
import ovh.eukon05.lokit.commonsecurity.LokitSecurityConfigurer;

@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties(LokitJwtProperties.class)
public class SecurityConfig {
    private final LokitSecurityConfigurer lokitSecurityConfigurer;

    public SecurityConfig(LokitJwtProperties lokitJwtProperties) {
        this.lokitSecurityConfigurer = new LokitSecurityConfigurer(lokitJwtProperties);
    }

    @Bean
    SecurityFilterChain httpSecurityConfig(HttpSecurity http) {
        return lokitSecurityConfigurer.configureHttpSecurity(http);
    }

    @Bean
    @GlobalServerInterceptor
    AuthenticationProcessInterceptor grpcSecurityConfig(GrpcSecurity grpc) throws Exception {
        return lokitSecurityConfigurer.configureGrpcSecurity(grpc);
    }
}
