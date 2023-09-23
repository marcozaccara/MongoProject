package mongo.validator;

import java.util.List;

public interface CustomValidator<T> {

    List<ValidationError> validate(T objectToValidate);
}
