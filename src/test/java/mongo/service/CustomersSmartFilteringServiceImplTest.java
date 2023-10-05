package mongo.service;

import mongo.dto.CustomerDTO;
import mongo.dto.FilterDTO;
import mongo.mapper.DtoMapper;
import mongo.mapper.EntityMapper;
import mongo.model.CriteriaEntity;
import mongo.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomersSmartFilteringServiceImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private DtoMapper<CustomerDTO, Customer> mapper;

    @Mock
    private EntityMapper<FilterDTO, CriteriaEntity> criteriaMapper;

    @Mock
    private CriteriaFactory criteriaFactory;

    @InjectMocks
    private CustomersSmartFilteringServiceImpl sut;

    private final FilterDTO filterDto = FilterDTO.builder().build();
    private final Customer customer = Customer.builder().build();
    private final CustomerDTO customerDto = CustomerDTO.builder().build();
    private final CriteriaEntity criteriaEntity = CriteriaEntity.builder().build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given CustomerSmartFilerringServiceImpl, When filterByCriteria is invoked, Then list of customer return is expected")
    void testFilterByCriteriaOk() {
        when(criteriaMapper.toEntity(filterDto)).thenReturn(criteriaEntity);
        when(criteriaFactory.create(criteriaEntity)).thenReturn(new Criteria());
        when(mongoTemplate.find(any(Query.class), any())).thenReturn(Collections.singletonList(customer));
        when(mapper.toDto(customer)).thenReturn(customerDto);

        List<CustomerDTO> result = sut.filterByCriteria(Collections.singletonList(filterDto));

        assertFalse(result.isEmpty());
        assertTrue(result.contains(customerDto));
    }

    @Test
    @DisplayName("Given CustomerSmartFilerringServiceImpl, When filterByCriteria is invoked, Then list of customer empty is expected")
    void testFilterByCriteriaEmptyList() {
        when(criteriaMapper.toEntity(filterDto)).thenReturn(criteriaEntity);
        when(criteriaFactory.create(criteriaEntity)).thenReturn(new Criteria());
        when(mongoTemplate.find(any(Query.class), any())).thenReturn(Collections.emptyList());

        List<CustomerDTO> result = sut.filterByCriteria(Collections.singletonList(filterDto));

        assertTrue(result.isEmpty());
    }

}