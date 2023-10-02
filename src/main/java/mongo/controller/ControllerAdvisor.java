package mongo.controller;

import mongo.dto.FailureResponse;
import mongo.exception.CustomValidationException;
import mongo.validator.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ControllerAdvice class that intercepts exception and build a ResponseEntity with custom status and body according to {@link FailureResponse} structure
 */
@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailureResponse> handleException(Exception e, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildFailureResponse(req)
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailureResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest req) {
        return ResponseEntity.badRequest()
                .body(buildFailureResponse(req)
                        .validationError(buildValidationError(e))
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FailureResponse> handleConstraintValidationException(ConstraintViolationException e, HttpServletRequest req) {
        return ResponseEntity.badRequest()
                .body(buildFailureResponse(req)
                        .validationError(buildValidationError(e))
                        .build());
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<FailureResponse> handleConstraintValidationException(CustomValidationException e, HttpServletRequest req) {
        return ResponseEntity.badRequest()
                .body(buildFailureResponse(req)
                        .validationError(e.getErrors())
                        .build());
    }

    private FailureResponse.FailureResponseBuilder buildFailureResponse(HttpServletRequest req) {
        return FailureResponse.builder()
                .uri(req.getRequestURI())
                .timeStamp(LocalDateTime.now());
    }

    private List<ValidationError> buildValidationError(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(er -> ValidationError.builder()
                        .field(er.getPropertyPath().toString())
                        .error(er.getMessageTemplate())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ValidationError> buildValidationError(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(er -> ValidationError.builder()
                        .field(((FieldError) er).getField())
                        .error(er.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

    }
}
