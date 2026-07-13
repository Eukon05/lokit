package ovh.eukon05.lokit.commonsecurity;

import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class LokitJwtConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String ROLE_FORMAT = "ROLE_%s";
    private final List<String> claims;
    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    public LokitJwtConverter(List<String> claims) {
        this.claims = claims == null ? List.of() : claims;
    }

    @Override
    public Collection<GrantedAuthority> convert(@NonNull Jwt source) {
        Collection<GrantedAuthority> authorities = defaultGrantedAuthoritiesConverter.convert(source);

        for (String claim : claims) {
            List<SimpleGrantedAuthority> roles = extractRoles(source, claim).stream()
                    .map(r -> new SimpleGrantedAuthority(ROLE_FORMAT.formatted(r)))
                    .toList();
            authorities.addAll(roles);
        }

        return authorities;
    }

    private List<String> extractRoles(Jwt source, String claimPath) {
        if (claimPath == null || claimPath.isBlank()) {
            return List.of();
        }

        String[] path = claimPath.split("\\.");
        Object current = source.getClaim(path[0]);

        for (int i = 1; i < path.length; i++) {
            if (!(current instanceof Map<?, ?> map)) {
                return List.of();
            }

            current = map.get(path[i]);
        }

        if (!(current instanceof Collection<?> roles)) {
            return List.of();
        }

        return roles.stream()
                .filter(Objects::nonNull)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();
    }
}
