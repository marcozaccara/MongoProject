package mongo.service;

import mongo.enumerator.OperatorEnum;
import mongo.model.CriteriaEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Factory to instance different {@link Criteria} based on {@link OperatorEnum} in input
 **/
@Component
public class CriteriaFactory {

    public Criteria create(CriteriaEntity criteriaEntity) {
        Criteria criteria = Criteria.where(criteriaEntity.getField());
        OperatorEnum operatorEnum = criteriaEntity.getOperator();
        Object value = criteriaEntity.getValue();
        if (OperatorEnum.NOT_EQUALS.equals(operatorEnum)) {

            return criteria.ne(value);
        }
        if (OperatorEnum.LESS_THAN.equals(operatorEnum)) {

            return criteria.lt(value);
        }
        if (OperatorEnum.LESS_THAN_EQUALS.equals(operatorEnum)) {

            return criteria.lte(value);
        }
        if (OperatorEnum.GREATER_THAN.equals(operatorEnum)) {

            return criteria.gt(value);
        }
        if (OperatorEnum.GREATER_THAN_EQUALS.equals(operatorEnum)) {

            return criteria.gte(value);
        }
        if (OperatorEnum.IN.equals(operatorEnum)) {

            return criteria.in(value);
        }
        if (OperatorEnum.NOT_IN.equals(operatorEnum)) {

            return criteria.nin(value);
        }
        if (OperatorEnum.MATCHES.equals(operatorEnum)) {

            return criteria.regex(Pattern.compile(value.toString(), Pattern.CASE_INSENSITIVE));
        }
        return criteria.is(value);
    }
}
