package mongo.mapper;

import mongo.dto.Dto;
import mongo.model.Entity;

import java.util.Map;

public interface Mapper<D extends Dto, E extends Entity> {

    D toDto(E entity, Object... additionalInfo);

    E toEntity(D dto, Object... additionalInfo);

}
