package ovh.eukon05.lokit.cardservice.exception;

import io.grpc.StatusRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ovh.eukon05.lokit.common.response.ApiErrorDTO;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ApiErrorDTO> handleStatusRuntimeException(StatusRuntimeException exception, HttpServletRequest request) {
        HttpStatus status = switch (exception.getStatus().getCode()) {
            case INVALID_ARGUMENT -> HttpStatus.BAD_REQUEST;
            case INTERNAL -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.SERVICE_UNAVAILABLE;
        };

        ApiErrorDTO errorDTO = buildErrorResponse(request, status, exception.getStatus().getCode().toString(), Collections.emptyMap());

        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleCardNotFound(CardNotFoundException exception, HttpServletRequest request) {
        ApiErrorDTO errorDTO = buildErrorResponse(request, HttpStatus.NOT_FOUND, exception.getMessage(), Collections.emptyMap());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleUserNotFound(UserNotFoundException exception, HttpServletRequest request) {
        ApiErrorDTO errorDTO = buildErrorResponse(request, HttpStatus.NOT_FOUND, exception.getMessage(), Collections.emptyMap());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors())
            errors.put(error.getField(), error.getDefaultMessage());

        ApiErrorDTO errorDTO = buildErrorResponse(request, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiErrorDTO> handleHandlerMethodValidationException(HandlerMethodValidationException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        for (ParameterValidationResult result : exception.getParameterValidationResults()) {
            String parameterName = result.getMethodParameter().getParameterName();
            result.getResolvableErrors().forEach(error -> errors.put(parameterName, error.getDefaultMessage()));
        }

        ApiErrorDTO errorDTO = buildErrorResponse(request, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put(exception.getName(), exception.getMostSpecificCause().getMessage());

        ApiErrorDTO errorDTO = buildErrorResponse(request, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorDTO> handleHttpMessageNotReadableException(HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("body", "Malformed request body");

        ApiErrorDTO errorDTO = buildErrorResponse(request, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    private ApiErrorDTO buildErrorResponse(HttpServletRequest request, HttpStatus status, String message, Map<String, String> errors) {
        return new ApiErrorDTO(Instant.now(), status.value(), message, request.getRequestURI(), errors);
    }
}
