package mongo.service;

import mongo.enumerator.OperatorEnum;
import mongo.model.CriteriaEntity;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.query.Criteria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Constants.EMPTY_STRING;

class CriteriaFactoryTest {

    private CriteriaFactory sut;

    @BeforeEach
    void setUp(){
        sut = new CriteriaFactory();
    }

    @Test
    @DisplayName("Given CriteriaFactory, When a CriteriaEntity is passed in input with operator NOT_EQUALS, Then CriteriaObject return in $ne")
    void testCriteriaNe(){
        CriteriaEntity criteriaEntity = CriteriaEntity.builder().field(EMPTY_STRING).operator(OperatorEnum.NOT_EQUALS).value(EMPTY_STRING).build();
        Criteria result = sut.create(criteriaEntity);
        assertEquals(new Document("$ne", EMPTY_STRING), result.getCriteriaObject());
    }

    @Test
    @DisplayName("Given CriteriaFactory, When a CriteriaEntity is passed in input with operator LESS_THAN, Then CriteriaObject return in $lt")
    void testCriteriaLt(){
        CriteriaEntity criteriaEntity = CriteriaEntity.builder().field(EMPTY_STRING).operator(OperatorEnum.LESS_THAN).value(EMPTY_STRING).build();
        Criteria result = sut.create(criteriaEntity);
        assertEquals(new Document("$lt", EMPTY_STRING), result.getCriteriaObject());
    }

    @Test
    @DisplayName("Given CriteriaFactory, When a CriteriaEntity is passed in input with operator LESS_THAN_EQUALS, Then CriteriaObject return in $lte")
    void testCriteriaLte(){
        CriteriaEntity criteriaEntity = CriteriaEntity.builder().field(EMPTY_STRING).operator(OperatorEnum.LESS_THAN_EQUALS).value(EMPTY_STRING).build();
        Criteria result = sut.create(criteriaEntity);
        assertEquals(new Document("$lte", EMPTY_STRING), result.getCriteriaObject());
    }

    @Test
    @DisplayName("Given CriteriaFactory, When a CriteriaEntity is passed in input with operator GREATER_THAN, Then CriteriaObject return in $gt")
    void testCriteriaGt(){
        CriteriaEntity criteriaEntity = CriteriaEntity.builder().field(EMPTY_STRING).operator(OperatorEnum.GREATER_THAN).value(EMPTY_STRING).build();
        Criteria result = sut.create(criteriaEntity);
        assertEquals(new Document("$gt", EMPTY_STRING), result.getCriteriaObject());
    }

    @Test
    @DisplayName("Given CriteriaFactory, When a CriteriaEntity is passed in input with operator GREATER_THAN_EQUALS, Then CriteriaObject return in $gte")
    void testCriteriaGte(){
        CriteriaEntity criteriaEntity = CriteriaEntity.builder().field(EMPTY_STRING).operator(OperatorEnum.GREATER_THAN_EQUALS).value(EMPTY_STRING).build();
        Criteria result = sut.create(criteriaEntity);
        assertEquals(new Document("$gte", EMPTY_STRING), result.getCriteriaObject());
    }

    @Test
    @DisplayName("Given CriteriaFactory, When a CriteriaEntity is passed in input with operator IN, Then CriteriaObject return in $in")
    void testCriteriaIn(){
        CriteriaEntity criteriaEntity = CriteriaEntity.builder().field(EMPTY_STRING).operator(OperatorEnum.IN).value(EMPTY_STRING).build();
        Criteria result = sut.create(criteriaEntity);
        assertEquals("", result.getKey());
    }

    @Test
    @DisplayName("Given CriteriaFactory, When a CriteriaEntity is passed in input with operator NOT_IN, Then CriteriaObject return in $nin")
    void testCriteriaNotIn(){
        CriteriaEntity criteriaEntity = CriteriaEntity.builder().field(EMPTY_STRING).operator(OperatorEnum.NOT_IN).value(EMPTY_STRING).build();
        Criteria result = sut.create(criteriaEntity);
        assertEquals("", result.getKey());
    }

    @Test
    @DisplayName("Given CriteriaFactory, When a CriteriaEntity is passed in input with operator MATCHES, Then CriteriaObject return in $regex")
    void testCriteriaRegex(){
        CriteriaEntity criteriaEntity = CriteriaEntity.builder().field(EMPTY_STRING).operator(OperatorEnum.MATCHES).value(EMPTY_STRING).build();
        Criteria result = sut.create(criteriaEntity);
        assertEquals("", result.getKey());
    }

    @Test
    @DisplayName("Given CriteriaFactory, When a CriteriaEntity is passed in input with operator EQUALS, Then CriteriaObject return in $is")
    void testCriteriaIs(){
        CriteriaEntity criteriaEntity = CriteriaEntity.builder().field(EMPTY_STRING).operator(OperatorEnum.EQUALS).value(EMPTY_STRING).build();
        Criteria result = sut.create(criteriaEntity);
        assertEquals("", result.getKey());
    }

}