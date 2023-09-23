package mongo.controller;

import lombok.var;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e, HttpServletRequest req) {
        if (e instanceof ValidationException) {
            return ResponseEntity.badRequest().body(buildErrorMap(e, req));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorMap(e, req));
    }

    private Map<String, Object> buildErrorMap(Exception e, HttpServletRequest req) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("timeStamp", LocalDateTime.now());
        errorMap.put("errorMessage", e.getMessage());
        errorMap.put("URI", req.getRequestURI());
        return errorMap;
    }
}
