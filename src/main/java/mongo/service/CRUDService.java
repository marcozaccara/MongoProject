package mongo.service;

import mongo.dto.CustomerDTO;
import mongo.enumerator.SortField;

import java.util.List;

public interface CRUDService<T> {

    List<CustomerDTO> findAll(Integer page, Integer size, SortField sortField);
    CustomerDTO upsert(CustomerDTO customer);
    void deleteById(String id);
}
