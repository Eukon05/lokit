package ovh.eukon05.lokit.roleservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ovh.eukon05.lokit.common.dto.ApiErrorDTO;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleRoleNotFound(RoleNotFoundException exception, HttpServletRequest request) {
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put(exception.getName(), exception.getMostSpecificCause().getMessage());

        ApiErrorDTO errorDTO = buildErrorResponse(request, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    private ApiErrorDTO buildErrorResponse(HttpServletRequest request, HttpStatus status, String message, Map<String, String> errors) {
        return new ApiErrorDTO(Instant.now(), status.value(), message, request.getRequestURI(), errors);
    }
}
