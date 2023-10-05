package mongo.mapper;

import mongo.dto.CustomerDTO;
import mongo.model.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

/**
 * Mapper to map {@link Customer} entity to {@link CustomerDTO} and vice-versa
 */
@Component
public class CustomerMapper implements DtoMapper<CustomerDTO, Customer> {
    @Override
    public CustomerDTO toDto(Customer entity, Object... additionalInfo) {
        return CustomerDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .address(entity.getAddress())
                .email(entity.getEmail())
                .age(Optional.ofNullable(entity.getBirthdate()).map(this::calculateAgeFromDate).orElse(null))
                .build();
    }

    @Override
    public Customer toEntity(CustomerDTO dto, Object... additionalInfo) {
        return Customer.builder()
                .id(dto.getId())
                .name(dto.getName())
                .username(dto.getUsername())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .birthdate(dto.getBirthDate())
                .build();
    }

    private Integer calculateAgeFromDate(LocalDateTime date) {
        LocalDate localDate = date.toLocalDate();
        LocalDate localDateNow = LocalDateTime.now().toLocalDate();
        return Period.between(localDate, localDateNow).getYears();
    }
}
