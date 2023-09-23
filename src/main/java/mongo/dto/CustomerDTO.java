package mongo.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
public class CustomerDTO implements Dto{

    private String id;
    private String username;
    private String name;
    private String address;
    private String email;
}
