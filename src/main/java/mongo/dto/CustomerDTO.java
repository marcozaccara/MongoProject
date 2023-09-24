package mongo.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@EqualsAndHashCode
@Getter
public class CustomerDTO implements Dto {

    private String id;
    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be blank")
    private String username;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be blank")
    private String name;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be blank")
    private String address;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be blank")
    private String email;
}
