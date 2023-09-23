package mongo.service;

import mongo.dto.CustomerDTO;
import mongo.exception.SaveEntityException;
import mongo.mapper.Mapper;
import mongo.model.Customer;
import mongo.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersService implements CRUDService<CustomerDTO> {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private Mapper<CustomerDTO, Customer> mapper;

    @Override
    public List<CustomerDTO> findAll(Integer page, Integer size, String sort) {

        Integer optionalPage = Optional.ofNullable(page).orElse(10);
        Integer optionalSize = Optional.ofNullable(size).orElse(10);
        String optionalPropertySort = Optional.ofNullable(sort).orElse("_id");

        return customersRepository.findAll(PageRequest.of(optionalPage, optionalSize, Sort.by(optionalPropertySort)))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CustomerDTO upsert(CustomerDTO customer) {
        return Optional.of(customer)
                .map(mapper::toEntity)
                .map(customersRepository::save)
                .map(mapper::toDto).orElseThrow(() -> new SaveEntityException("Error during save customer"));
    }

    @Override
    public void deleteById(String id) {
        customersRepository.deleteById(id);
    }
}
