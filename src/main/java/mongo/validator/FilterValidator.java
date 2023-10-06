package mongo.validator;

import mongo.dto.FilterDTO;
import mongo.enumerator.FieldEnum;
import mongo.enumerator.OperatorEnum;
import org.springframework.stereotype.Component;

import java.util.*;

import static mongo.enumerator.FieldEnum.*;
import static mongo.enumerator.OperatorEnum.*;

/**
 * Validator to validate FilterDto for smart filtering
 */
@Component
public class FilterValidator implements CustomValidator<FilterDTO> {

    private static final Set<FieldEnum> SET_OF_STRING_FIELDS = new HashSet<>(Arrays.asList(USERNAME, NAME, ADDRESS, EMAIL));
    private static final Set<FieldEnum> SET_OF_NUMERIC_FIELDS = new HashSet<>(Collections.singletonList(AGE));
    private static final Set<OperatorEnum> SET_OF_OPERATION_ALLOWED_FOR_STRING = new HashSet<>(Arrays.asList(EQUALS, NOT_EQUALS, MATCHES));
    private static final Set<OperatorEnum> SET_OF_OPERATION_ALLOWED_FOR_NUMERIC = new HashSet<>(Arrays.asList(NOT_EQUALS, LESS_THAN, LESS_THAN_EQUALS, GREATER_THAN, GREATER_THAN_EQUALS));
    private static final Set<OperatorEnum> SET_OF_OPERATION_ALLOWED_FOR_COLLECTION = new HashSet<>(Arrays.asList(IN, NOT_IN));

    @Override
    public List<ValidationError> validate(FilterDTO objectToValidate) {
        List<ValidationError> errors = new ArrayList<>();
        FieldEnum fieldEnum = objectToValidate.getField();
        OperatorEnum operatorEnum = objectToValidate.getOperator();
        Object value = objectToValidate.getValue();
        if (!(isOperationValidForStringField(fieldEnum, operatorEnum) || isOperationValidForNumericField(fieldEnum, operatorEnum) || isOperationValidForCollectionField(fieldEnum, operatorEnum))) {
            errors.add(ValidationError.builder().field(fieldEnum.toString()).error("operation not valid for this field").build());
            return errors;
        }
        if (isOperationValidForStringField(fieldEnum, operatorEnum) && !(value instanceof String)) {
            errors.add(ValidationError.builder().field(fieldEnum.toString()).error("value must be a string").build());
            return errors;
        }

        if (isOperationValidForNumericField(fieldEnum, operatorEnum) && !(value instanceof Number)) {
            errors.add(ValidationError.builder().field(fieldEnum.toString()).error("value must be a number").build());
            return errors;
        }

        if (isOperationValidForCollectionField(fieldEnum, operatorEnum) && !(value instanceof Collection)) {
            errors.add(ValidationError.builder().field(fieldEnum.toString()).error("value must be a list").build());
            return errors;
        }

        return errors;
    }

    private static boolean isOperationValidForNumericField(FieldEnum fieldEnum, OperatorEnum operatorEnum) {
        return isNumericField(fieldEnum) && isOperationAllowedForNumericField(operatorEnum);
    }

    private static boolean isOperationValidForStringField(FieldEnum fieldEnum, OperatorEnum operatorEnum) {
        return isStringField(fieldEnum) && isOperationAllowedForStringField(operatorEnum);
    }

    private static boolean isOperationValidForCollectionField(FieldEnum fieldEnum, OperatorEnum operatorEnum) {
        return isStringField(fieldEnum) && isOperationAllowedForCollectionField(operatorEnum);
    }

    private static boolean isStringField(FieldEnum field) {
        return SET_OF_STRING_FIELDS.contains(field);
    }

    private static boolean isNumericField(FieldEnum field) {
        return SET_OF_NUMERIC_FIELDS.contains(field);
    }

    private static boolean isOperationAllowedForStringField(OperatorEnum operation) {
        return SET_OF_OPERATION_ALLOWED_FOR_STRING.contains(operation);
    }

    private static boolean isOperationAllowedForNumericField(OperatorEnum operation) {
        return SET_OF_OPERATION_ALLOWED_FOR_NUMERIC.contains(operation);
    }

    private static boolean isOperationAllowedForCollectionField(OperatorEnum operator) {
        return SET_OF_OPERATION_ALLOWED_FOR_COLLECTION.contains(operator);
    }
}
