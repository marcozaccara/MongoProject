package mongo.mapper;

import mongo.dto.FilterDTO;
import mongo.enumerator.FieldEnum;
import mongo.enumerator.OperatorEnum;
import mongo.model.CriteriaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static utils.Constants.BIRTHDATE;
import static utils.Constants.EMPTY_STRING;

class CriteriaMapperTest {

    private CriteriaMapper sut;

    @BeforeEach
    void setUp() {
        sut = new CriteriaMapper();
    }

    @Test
    @DisplayName("Given the CriteriaMapper, when a FilterDto is passed with field != AGE, then CriteriaEntity is as expected with field lowercase")
    void testMapToEntityFieldNoAge() {
        FilterDTO filterDto = FilterDTO.builder().field(FieldEnum.NAME).operator(OperatorEnum.GREATER_THAN).value(EMPTY_STRING).build();

        CriteriaEntity result = sut.toEntity(filterDto);

        assertEquals("name", result.getField());
        assertEquals(OperatorEnum.GREATER_THAN, result.getOperator());
        assertEquals(EMPTY_STRING, result.getValue());

    }

    @Test
    @DisplayName("Given the CriteriaMapper, when a FilterDto is passed with field == AGE and operator == GREATER_THAN_EQUALS, then CriteriaEntity has field 'birthday' and operator switched")
    void testMapToEntityFieldAgeGreaterThanEquals() {
        FilterDTO filterDto = FilterDTO.builder().field(FieldEnum.AGE).operator(OperatorEnum.GREATER_THAN_EQUALS).value(0).build();

        CriteriaEntity result = sut.toEntity(filterDto);

        assertEquals(BIRTHDATE, result.getField());
        assertEquals(OperatorEnum.LESS_THAN, result.getOperator());
        assertNotNull(result.getValue());

    }

    @Test
    @DisplayName("Given the CriteriaMapper, when a FilterDto is passed with field == AGE and operator == GREATER_THAN, then CriteriaEntity has field 'birthday' and operator switched")
    void testMapToEntityFieldAgeGreaterThan() {
        FilterDTO filterDto = FilterDTO.builder().field(FieldEnum.AGE).operator(OperatorEnum.GREATER_THAN).value(0).build();

        CriteriaEntity result = sut.toEntity(filterDto);

        assertEquals(BIRTHDATE, result.getField());
        assertEquals(OperatorEnum.LESS_THAN_EQUALS, result.getOperator());
        assertNotNull(result.getValue());

    }

    @Test
    @DisplayName("Given the CriteriaMapper, when a FilterDto is passed with field == AGE and operator == LESS_THAN, then CriteriaEntity has field 'birthday' and operator switched")
    void testMapToEntityFieldAgeLessThan() {
        FilterDTO filterDto = FilterDTO.builder().field(FieldEnum.AGE).operator(OperatorEnum.LESS_THAN).value(0).build();

        CriteriaEntity result = sut.toEntity(filterDto);

        assertEquals(BIRTHDATE, result.getField());
        assertEquals(OperatorEnum.GREATER_THAN_EQUALS, result.getOperator());
        assertNotNull(result.getValue());

    }

    @Test
    @DisplayName("Given the CriteriaMapper, when a FilterDto is passed with field == AGE and operator == LESS_THAN_EQUALS, then CriteriaEntity has field 'birthday' and operator switched")
    void testMapToEntityFieldAgeLessThanEqual() {
        FilterDTO filterDto = FilterDTO.builder().field(FieldEnum.AGE).operator(OperatorEnum.LESS_THAN_EQUALS).value(0).build();

        CriteriaEntity result = sut.toEntity(filterDto);

        assertEquals(BIRTHDATE, result.getField());
        assertEquals(OperatorEnum.GREATER_THAN, result.getOperator());
        assertNotNull(result.getValue());

    }

    @Test
    @DisplayName("Given the CriteriaMapper, when a FilterDto is passed with field == AGE and operator == NOT_EQUALS, then CriteriaEntity has field 'birthday' and Operator NOT_EQUALS")
    void testMapToEntityFieldAgeNotEquals() {
        FilterDTO filterDto = FilterDTO.builder().field(FieldEnum.AGE).operator(OperatorEnum.NOT_EQUALS).value(0).build();

        CriteriaEntity result = sut.toEntity(filterDto);

        assertEquals(BIRTHDATE, result.getField());
        assertEquals(OperatorEnum.NOT_EQUALS, result.getOperator());
        assertNotNull(result.getValue());

    }
}