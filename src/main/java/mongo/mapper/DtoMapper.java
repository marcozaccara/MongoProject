package mongo.mapper;

import mongo.dto.Dto;
import mongo.model.Entity;

/**
 * Interface to map entity to dto
 *
 * @param <D>
 * @param <E>
 */
public interface DtoMapper<D extends Dto, E extends Entity> extends EntityMapper<D, E> {

    D toDto(E entity, Object... additionalInfo);

}
