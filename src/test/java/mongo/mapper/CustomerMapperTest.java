package mongo.mapper;

import mongo.dto.CustomerDTO;
import mongo.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.EMPTY_STRING;

class CustomerMapperTest {

    private CustomerMapper sut;

    @BeforeEach
    void setUp(){
        sut = new CustomerMapper();
    }

    @Test
    @DisplayName("Given CustomerMapper, when CustomerEntity is passed in input with birthdate filled, CustomerDTO has fields as expected")
    void testMapToDto(){
        Customer customer = Customer.builder()
                .id(EMPTY_STRING)
                .name(EMPTY_STRING)
                .username(EMPTY_STRING)
                .address(EMPTY_STRING)
                .email(EMPTY_STRING)
                .birthdate(LocalDateTime.now())
                .build();

        CustomerDTO result = sut.toDto(customer);

        assertEquals(EMPTY_STRING, result.getId());
        assertEquals(EMPTY_STRING, result.getName());
        assertEquals(EMPTY_STRING, result.getUsername());
        assertEquals(EMPTY_STRING, result.getEmail());
        assertEquals(EMPTY_STRING, result.getAddress());
        assertNull(result.getBirthDate());
        assertNotNull(result.getAge());
    }

    @Test
    @DisplayName("Given CustomerMapper, when CustomerEntity is passed in input with birthdate null, CustomerDTO has fields as expected")
    void testMapToDtoBirthdateNull(){
        Customer customer = Customer.builder()
                .id(EMPTY_STRING)
                .name(EMPTY_STRING)
                .username(EMPTY_STRING)
                .address(EMPTY_STRING)
                .email(EMPTY_STRING)
                .birthdate(null)
                .build();

        CustomerDTO result = sut.toDto(customer);

        assertEquals(EMPTY_STRING, result.getId());
        assertEquals(EMPTY_STRING, result.getName());
        assertEquals(EMPTY_STRING, result.getUsername());
        assertEquals(EMPTY_STRING, result.getEmail());
        assertEquals(EMPTY_STRING, result.getAddress());
        assertNull(result.getBirthDate());
        assertNull(result.getAge());
    }

    @Test
    @DisplayName("Given CustomerMapper, when CustomerDTO is passed in input, CustomerEntity has fields as expected")
    void testMapToEntityBirthdateNull(){
        CustomerDTO customerDto = CustomerDTO.builder()
                .id(EMPTY_STRING)
                .name(EMPTY_STRING)
                .username(EMPTY_STRING)
                .address(EMPTY_STRING)
                .email(EMPTY_STRING)
                .birthDate(LocalDateTime.now())
                .age(1)
                .build();

        Customer result = sut.toEntity(customerDto);

        assertEquals(EMPTY_STRING, result.getId());
        assertEquals(EMPTY_STRING, result.getName());
        assertEquals(EMPTY_STRING, result.getUsername());
        assertEquals(EMPTY_STRING, result.getEmail());
        assertEquals(EMPTY_STRING, result.getAddress());
        assertNotNull(result.getBirthdate());
    }

}