package ovh.eukon05.lokit.common.dto;

import java.time.Instant;
import java.util.Map;

public record ApiErrorDTO(Instant timestamp, int status, String message, String path, Map<String, String> errors) {
}
