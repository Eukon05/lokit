package ovh.eukon05.lokit.identityservice.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KeycloakProperties.class)
@RequiredArgsConstructor
public class KeycloakConfig {
    private final KeycloakProperties properties;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(properties.serverUrl())
                .realm(properties.realm())
                .clientId(properties.clientId())
                .clientSecret(properties.clientSecret())
                .grantType(OAuth2Constants.PASSWORD)
                .username(properties.username())
                .password(properties.password())
                .build();
    }

}
