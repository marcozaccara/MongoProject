package mongo.validator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Validator pojo to represent a validation error
 */
@Builder
@Getter
@EqualsAndHashCode
public class ValidationError {

    private String field;
    private String error;
}
