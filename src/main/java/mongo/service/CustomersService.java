package mongo.service;

import mongo.dto.CustomerDTO;
import mongo.enumerator.SortField;
import mongo.exception.SaveEntityException;
import mongo.mapper.DtoMapper;
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
    private DtoMapper<CustomerDTO, Customer> mapper;

    @Override
    public List<CustomerDTO> findAll(Integer size, SortField sortField) {

        Integer sizeValue = Optional.ofNullable(size).orElse(10);
        String sortPropertyField = Optional.ofNullable(sortField).map(Enum::toString).orElse(SortField.id.toString());

        return customersRepository.findAll(PageRequest.of(0, sizeValue, Sort.by(sortPropertyField)))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CustomerDTO upsert(CustomerDTO customer) {
        return Optional.ofNullable(customer)
                .map(mapper::toEntity)
                .map(customersRepository::save)
                .map(mapper::toDto).orElseThrow(() -> new SaveEntityException("Error during save customer"));
    }

    @Override
    public void deleteById(String id) {
        customersRepository.deleteById(id);
    }
}
