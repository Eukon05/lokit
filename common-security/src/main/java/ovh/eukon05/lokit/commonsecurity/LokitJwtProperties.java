package ovh.eukon05.lokit.commonsecurity;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "lokit.jwt")
record LokitJwtProperties(List<String> roleClaims) {
}
