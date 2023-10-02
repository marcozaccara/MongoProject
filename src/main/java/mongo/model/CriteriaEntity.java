package mongo.model;

import lombok.Builder;
import lombok.Getter;
import mongo.enumerator.OperatorEnum;

@Builder
@Getter
public class CriteriaEntity implements Entity {

    private String field;
    private OperatorEnum operator;
    private Object value;
}
