package ovh.eukon05.lokit.deviceservice.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "lokit.mqtt")
@Validated
public record LokitMqttProperties(@NotBlank String serverUrl, @NotBlank String clientId, @NotBlank String username,
                                  @NotBlank String password) {
}
