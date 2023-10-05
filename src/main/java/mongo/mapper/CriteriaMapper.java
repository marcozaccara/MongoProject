package mongo.mapper;

import mongo.dto.FilterDTO;
import mongo.enumerator.FieldEnum;
import mongo.enumerator.OperatorEnum;
import mongo.model.CriteriaEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static utils.Constants.BIRTHDATE;

/**
 * Mapper to map {@link FilterDTO} to {@link CriteriaEntity}
 */
@Component
public class CriteriaMapper implements EntityMapper<FilterDTO, CriteriaEntity> {

    @Override
    public CriteriaEntity toEntity(FilterDTO dto, Object... additionalInfo) {
        FieldEnum field = dto.getField();
        if (!FieldEnum.AGE.equals(field)) {
            return CriteriaEntity.builder()
                    .field(dto.getField().toString().toLowerCase())
                    .operator(dto.getOperator())
                    .value(dto.getValue())
                    .build();
        }
        return CriteriaEntity.builder()
                .field(BIRTHDATE)
                .operator(switchOperator(dto.getOperator()))
                .value(calculateDateFromYear((Integer) dto.getValue()))
                .build();
    }

    private static LocalDateTime calculateDateFromYear(Integer value) {
        return LocalDateTime.now().withYear(LocalDateTime.now().getYear() - value);
    }

    private static OperatorEnum switchOperator(OperatorEnum operator) {
        switch (operator) {
            case GREATER_THAN:
                return OperatorEnum.LESS_THAN_EQUALS;
            case GREATER_THAN_EQUALS:
                return OperatorEnum.LESS_THAN;
            case LESS_THAN:
                return OperatorEnum.GREATER_THAN_EQUALS;
            case LESS_THAN_EQUALS:
                return OperatorEnum.GREATER_THAN;
            default:
                return OperatorEnum.NOT_EQUALS;
        }
    }
}
