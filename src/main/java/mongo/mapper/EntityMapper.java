package mongo.mapper;

import mongo.dto.Dto;
import mongo.model.Entity;

public interface EntityMapper<D extends Dto, E extends Entity> {

    E toEntity(D dto, Object... additionalInfo);
}
