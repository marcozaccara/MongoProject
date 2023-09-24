package mongo.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mongo.enumerator.OperatorEnum;

@Builder
@EqualsAndHashCode
@Getter
public class FilterDTO implements Dto {

    private String field;
    private OperatorEnum operator;
    private Object value;
    private boolean and;
}
