package mongo.service;

import mongo.dto.FilterDTO;
import mongo.enumerator.OperatorEnum;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Default Criteria is "is"
 **/
@Component
public class CriteriaFactory {

    public Criteria create(FilterDTO filterDto) {
        Criteria criteria = new Criteria();
        OperatorEnum operatorEnum = filterDto.getOperator();
        Object value = filterDto.getValue();
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
