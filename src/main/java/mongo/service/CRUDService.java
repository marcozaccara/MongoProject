package mongo.service;

import mongo.enumerator.SortField;

import java.util.List;

/**
 * CRUD interface. API exposed are findAll, upsert and delete
 *
 * @param <T>
 */
public interface CRUDService<T, ID> {

    List<T> findAll(Integer size, SortField sortField);

    T upsert(T customer);

    void deleteById(ID id);
}
