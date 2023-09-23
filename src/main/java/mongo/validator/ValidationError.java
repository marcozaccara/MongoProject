package mongo.validator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class ValidationError {

    private String field;
    private String error;
}
