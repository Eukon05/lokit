package ovh.eukon05.lokit.commonsecurity;

import org.springframework.grpc.server.security.AuthenticationProcessInterceptor;
import org.springframework.grpc.server.security.GrpcSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

public class LokitSecurityConfigurer {
    private final LokitJwtProperties lokitJwtProperties;

    public LokitSecurityConfigurer(LokitJwtProperties lokitJwtProperties) {
        this.lokitJwtProperties = lokitJwtProperties;
    }

    public SecurityFilterChain configureHttpSecurity(HttpSecurity http) {
        return http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(lokitJwtConverter())))
                .build();
    }

    public AuthenticationProcessInterceptor configureGrpcSecurity(GrpcSecurity grpc) throws Exception {
        return grpc.authorizeRequests(requests -> requests.allRequests().permitAll()).build();
    }

    private JwtAuthenticationConverter lokitJwtConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new LokitJwtConverter(lokitJwtProperties.roleClaims()));
        return converter;
    }
}
