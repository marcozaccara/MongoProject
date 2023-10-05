package mongo.mapper;

import mongo.dto.Dto;
import mongo.model.Entity;

/**
 * Interface to map dto to entity
 *
 * @param <D>
 * @param <E>
 */
public interface EntityMapper<D extends Dto, E extends Entity> {
    E toEntity(D dto, Object... additionalInfo);
}
