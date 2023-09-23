package mongo.service;

import mongo.dto.CustomerDTO;
import mongo.dto.FilterDTO;
import mongo.mapper.Mapper;
import mongo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomersSmartFilteringServiceImpl implements SmartFilteringService<CustomerDTO> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Mapper<CustomerDTO, Customer> mapper;

    @Override
    public List<CustomerDTO> filterByCriteria(List<FilterDTO> filters) {
        return mongoTemplate.find(new Query(Criteria.where("address").ne("")), Customer.class)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
