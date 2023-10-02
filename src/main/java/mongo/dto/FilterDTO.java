package mongo.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mongo.enumerator.FieldEnum;
import mongo.enumerator.OperatorEnum;

import javax.validation.constraints.NotNull;

/**
 * Dto filter that contains {@link FieldEnum} field, {@link OperatorEnum} operator and value
 */
@Builder
@EqualsAndHashCode
@Getter
public class FilterDTO implements Dto {

    @NotNull
    private FieldEnum field;
    @NotNull
    private OperatorEnum operator;
    @NotNull
    private Object value;
}
