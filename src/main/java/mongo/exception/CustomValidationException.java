package mongo.exception;

import mongo.validator.ValidationError;

import java.util.List;

public class CustomValidationException extends RuntimeException {

    private final List<ValidationError> errors;

    public CustomValidationException(List<ValidationError> errors) {
        super(errors.toString());
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
