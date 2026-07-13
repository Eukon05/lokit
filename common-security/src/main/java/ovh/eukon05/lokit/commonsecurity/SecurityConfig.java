package ovh.eukon05.lokit.commonsecurity;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties(LokitJwtProperties.class)
class SecurityConfig {
    private final LokitJwtProperties lokitJwtProperties;

    public SecurityConfig(LokitJwtProperties lokitJwtProperties) {
        this.lokitJwtProperties = lokitJwtProperties;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(lokitJwtConverter())))
                .build();
    }

    private JwtAuthenticationConverter lokitJwtConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new LokitJwtConverter(lokitJwtProperties.roleClaims()));
        return converter;
    }
}
