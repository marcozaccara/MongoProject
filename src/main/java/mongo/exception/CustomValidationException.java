package mongo.exception;

import lombok.Getter;
import mongo.validator.ValidationError;

import java.util.List;

/**
 * Exception that wraps list of {@link ValidationError}
 */
@Getter
public class CustomValidationException extends RuntimeException {

    private final List<ValidationError> errors;

    public CustomValidationException(List<ValidationError> errors) {
        super(errors.toString());
        this.errors = errors;
    }

}
