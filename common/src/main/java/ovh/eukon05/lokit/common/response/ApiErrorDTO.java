package ovh.eukon05.lokit.common.response;

import java.time.Instant;
import java.util.Map;

public record ApiErrorDTO(Instant timestamp, int status, String message, String path, Map<String, String> errors) {
}
