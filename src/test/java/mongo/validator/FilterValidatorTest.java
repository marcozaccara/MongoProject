package mongo.validator;

import mongo.dto.FilterDTO;
import mongo.enumerator.FieldEnum;
import mongo.enumerator.OperatorEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.EMPTY_STRING;

class FilterValidatorTest {

    private FilterValidator sut;

    @BeforeEach
    void setUp(){
        sut = new FilterValidator();
    }

    @Test
    @DisplayName("Given FilterValidator, When a filterDto is passed in input with valid operator for numeric field, Then empty list is returned")
    void testValidationReturnEmptyListValidOperatorForNumericField(){
        List<ValidationError> result = sut.validate(FilterDTO.builder().field(FieldEnum.AGE).operator(OperatorEnum.GREATER_THAN).value(0).build());

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Given FilterValidator, When a filterDto is passed in input with valid operator for string field, Then empty list is returned")
    void testValidationReturnEmptyListValidOperatorForStringField(){
        List<ValidationError> result = sut.validate(FilterDTO.builder().field(FieldEnum.ADDRESS).operator(OperatorEnum.MATCHES).value(EMPTY_STRING).build());

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Given FilterValidator, When a non valid operator for String field is passed in input filterDto, Then validation error list is not empty")
    void testValidationReturnErrorOperationNotValidForStringField(){
        List<ValidationError> result = sut.validate(FilterDTO.builder().field(FieldEnum.NAME).operator(OperatorEnum.GREATER_THAN).build());

        assertFalse(result.isEmpty());
        assertTrue(result.contains(ValidationError.builder().field(FieldEnum.NAME.name()).error("operation not valid for this field").build()));
    }

    @Test
    @DisplayName("Given FilterValidator, When a non valid operator for String field is passed in input filterDto, Then validation error list is not empty")
    void testValidationReturnErrorOperationNotValidForNumericField(){
        List<ValidationError> result = sut.validate(FilterDTO.builder().field(FieldEnum.AGE).operator(OperatorEnum.NOT_IN).build());

        assertFalse(result.isEmpty());
        assertTrue(result.contains(ValidationError.builder().field(FieldEnum.AGE.name()).error("operation not valid for this field").build()));
    }

    @Test
    @DisplayName("Given FilterValidator, When a non valid value for String field and operator is passed in input filterDto, Then validation error list is not empty")
    void testValidationReturnErrorValueIntegerNotValidForField(){
        List<ValidationError> result = sut.validate(FilterDTO.builder().field(FieldEnum.NAME).operator(OperatorEnum.MATCHES).value(0).build());

        assertFalse(result.isEmpty());
        assertTrue(result.contains(ValidationError.builder().field(FieldEnum.NAME.name()).error("value must be a string").build()));
    }

    @Test
    @DisplayName("Given FilterValidator, When a non valid value for Numeric field and operator is passed in input filterDto, Then validation error list is not empty")
    void testValidationReturnErrorValueStringNotValidForField(){
        List<ValidationError> result = sut.validate(FilterDTO.builder().field(FieldEnum.AGE).operator(OperatorEnum.LESS_THAN).value(EMPTY_STRING).build());

        assertFalse(result.isEmpty());
        assertTrue(result.contains(ValidationError.builder().field(FieldEnum.AGE.name()).error("value must be a number").build()));
    }

    @Test
    @DisplayName("Given FilterValidator, When a non valid value for Collection field and operator IN is passed in input filterDto, Then validation error list is not empty")
    void testValidationReturnErrorValueCollectionNotValidForField(){
        List<ValidationError> result = sut.validate(FilterDTO.builder().field(FieldEnum.EMAIL).operator(OperatorEnum.IN).value(EMPTY_STRING).build());

        assertFalse(result.isEmpty());
        assertTrue(result.contains(ValidationError.builder().field(FieldEnum.EMAIL.name()).error("value must be a list").build()));
    }

    @Test
    @DisplayName("Given FilterValidator, When a non valid value for Collection field and operator NOT_IN is passed in input filterDto, Then validation error list is not empty")
    void testValidationReturnErrorValueCollectionNotValidForField2(){
        List<ValidationError> result = sut.validate(FilterDTO.builder().field(FieldEnum.USERNAME).operator(OperatorEnum.NOT_IN).value(EMPTY_STRING).build());

        assertFalse(result.isEmpty());
        assertTrue(result.contains(ValidationError.builder().field(FieldEnum.USERNAME.name()).error("value must be a list").build()));
    }

}