package ovh.eukon05.lokit.identityservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lokit.keycloak")
record KeycloakProperties(String serverUrl, String realm, String clientId, String clientSecret) {
}
