package mongo.service;

import mongo.dto.CustomerDTO;
import mongo.enumerator.SortField;
import mongo.exception.SaveEntityException;
import mongo.mapper.DtoMapper;
import mongo.model.Customer;
import mongo.repository.CustomersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomersServiceTest {

    @Mock
    private CustomersRepository customersRepository;

    @Mock
    private DtoMapper<CustomerDTO, Customer> mapper;

    @InjectMocks
    private CustomersService sut;

    private final CustomerDTO customerDto = CustomerDTO.builder().build();
    private final Customer customer = Customer.builder().build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given CustomerService, When findAll is invoked, Then list of customer is returned")
    void testFindAllOk() {
        PageImpl<Customer> pages = new PageImpl<>(Collections.singletonList(customer));
        when(customersRepository.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(pages);
        when(mapper.toDto(customer)).thenReturn(customerDto);

        List<CustomerDTO> result = sut.findAll(10, SortField.id);

        assertFalse(result.isEmpty());
        assertTrue(result.contains(customerDto));
    }

    @Test
    @DisplayName("Given CustomerService, When findAll is invoked with parameters null, Then list of customer is returned")
    void testFindAllOkParametersNull() {
        PageImpl<Customer> pages = new PageImpl<>(Collections.singletonList(customer));
        when(customersRepository.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(pages);
        when(mapper.toDto(customer)).thenReturn(customerDto);

        List<CustomerDTO> result = sut.findAll(null, null);

        assertFalse(result.isEmpty());
        assertTrue(result.contains(customerDto));
    }

    @Test
    @DisplayName("Given CustomerService, When upsert is invoked, Then customer dto is returned")
    void testUpsertOk() {
        when(mapper.toEntity(customerDto)).thenReturn(customer);
        when(customersRepository.save(customer)).thenReturn(customer);
        when(mapper.toDto(customer)).thenReturn(customerDto);

        CustomerDTO result = sut.upsert(customerDto);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Given CustomerService, When upsert is invoked with parameters null, Then exception is thrown")
    void testUpsertKo() {
        assertThrows(SaveEntityException.class, () -> sut.upsert(null));
    }

    @Test
    @DisplayName("Given CustomerService, When delete is invoked, Then ok")
    void testDeleteok() {
        sut.deleteById("");

        verify(customersRepository, times(1)).deleteById("");
    }

}