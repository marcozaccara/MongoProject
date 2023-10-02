package mongo.service;

import mongo.dto.CustomerDTO;
import mongo.dto.FilterDTO;
import mongo.mapper.DtoMapper;
import mongo.mapper.EntityMapper;
import mongo.model.CriteriaEntity;
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
    private DtoMapper<CustomerDTO, Customer> mapper;

    @Autowired
    private EntityMapper<FilterDTO, CriteriaEntity> criteriaMapper;

    @Autowired
    private CriteriaFactory criteriaFactory;

    @Override
    public List<CustomerDTO> filterByCriteria(List<FilterDTO> filters) {
        Criteria criteria = createCriteria(filters);
        return mongoTemplate.find(new Query(criteria), Customer.class)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    private Criteria createCriteria(List<FilterDTO> filters) {
        Criteria criteria = new Criteria();
        return criteria.andOperator(filters.stream()
                .map(criteriaMapper::toEntity)
                .map(f -> criteriaFactory.create(f))
                .toArray(Criteria[]::new));
    }
}
