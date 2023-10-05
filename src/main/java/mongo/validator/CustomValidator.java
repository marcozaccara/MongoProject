package mongo.validator;

import java.util.List;

/**
 * Generic interface to validate a T object
 *
 * @param <T>
 */
public interface CustomValidator<T> {

    List<ValidationError> validate(T objectToValidate);
}
