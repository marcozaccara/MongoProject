package mongo.mapper;

import mongo.dto.CustomerDTO;
import mongo.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<CustomerDTO, Customer> {
    @Override
    public CustomerDTO toDto(Customer entity, Object... additionalInfo) {
        return CustomerDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .address(entity.getAddress())
                .email(entity.getEmail())
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
                .build();
    }
}
