package mongo.exception;

/**
 * Exception to throw in case Save operation does not work as expected
 */
public class SaveEntityException extends RuntimeException {

    public SaveEntityException(String e) {
        super(e);
    }
}
