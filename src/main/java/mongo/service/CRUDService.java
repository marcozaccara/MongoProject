package mongo.service;

import mongo.dto.CustomerDTO;

import java.util.List;

public interface CRUDService<T> {

    List<CustomerDTO> findAll(Integer page, Integer size, String sort);
    CustomerDTO upsert(CustomerDTO customer);
    void deleteById(String id);
}
