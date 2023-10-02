package mongo.mapper;

import mongo.dto.Dto;
import mongo.model.Entity;

public interface DtoMapper<D extends Dto, E extends Entity> extends EntityMapper<D, E> {

    D toDto(E entity, Object... additionalInfo);

}
