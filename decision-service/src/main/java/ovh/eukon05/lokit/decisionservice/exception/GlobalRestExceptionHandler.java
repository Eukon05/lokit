package ovh.eukon05.lokit.decisionservice.exception;

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
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class GlobalRestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors())
            errors.put(error.getField(), error.getDefaultMessage());

        ApiErrorDTO errorDTO = buildErrorResponse(request, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    ResponseEntity<ApiErrorDTO> handleHandlerMethodValidationException(HandlerMethodValidationException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        for (ParameterValidationResult result : exception.getParameterValidationResults()) {
            String parameterName = result.getMethodParameter().getParameterName();
            result.getResolvableErrors().forEach(error -> errors.put(parameterName, error.getDefaultMessage()));
        }

        ApiErrorDTO errorDTO = buildErrorResponse(request, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<ApiErrorDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put(exception.getName(), exception.getMostSpecificCause().getMessage());

        ApiErrorDTO errorDTO = buildErrorResponse(request, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ApiErrorDTO> handleHttpMessageNotReadableException(HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("body", "Malformed request body");

        ApiErrorDTO errorDTO = buildErrorResponse(request, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    private ApiErrorDTO buildErrorResponse(HttpServletRequest request, HttpStatus status, String message, Map<String, String> errors) {
        return new ApiErrorDTO(Instant.now(), status.value(), message, request.getRequestURI(), errors);
    }
}
