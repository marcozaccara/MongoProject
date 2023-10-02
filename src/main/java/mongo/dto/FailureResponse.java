package mongo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import mongo.validator.ValidationError;

import java.time.LocalDateTime;
import java.util.List;


/**
 * FailureResponse used as return object of controller methods.
 */
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FailureResponse {
    private LocalDateTime timeStamp;
    private List<ValidationError> validationError;
    private String errorMessage;
    private String uri;
}
