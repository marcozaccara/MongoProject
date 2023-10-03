package mongo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Dto class to represent Customer to View layer
 */
@Builder
@EqualsAndHashCode
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO implements Dto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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

    private LocalDateTime birthDate;

    private Integer age;
}
