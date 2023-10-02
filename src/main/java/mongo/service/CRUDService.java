package mongo.service;

import mongo.enumerator.SortField;

import java.util.List;

public interface CRUDService<T> {

    List<T> findAll(Integer size, SortField sortField);

    T upsert(T customer);

    void deleteById(String id);
}
