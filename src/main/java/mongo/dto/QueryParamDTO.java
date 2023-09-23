package mongo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QueryParamDTO {

    @Size(max = 500, message = "page min value is 0; page max value is 500")
    private Integer page;
    @Size(max = 500, message = "size min value is 0; page max value is 500")
    private Integer size;
    @Pattern(regexp = "username|name|password|email", message = "value must be in [username,name,password,email]", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String sort;
}
