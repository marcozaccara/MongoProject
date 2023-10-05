package mongo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import mongo.dto.CustomerDTO;
import mongo.dto.FilterDTO;
import mongo.enumerator.SortField;
import mongo.exception.CustomValidationException;
import mongo.service.CRUDService;
import mongo.service.SmartFilteringService;
import mongo.validator.CustomValidator;
import mongo.validator.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class that route HTTP requests to related Service
 */
@RestController
@RequestMapping("/customers")
@Api(value = "CRUD Rest APIs for customers")
@Validated
public class CustomersController {

    @Autowired
    private CRUDService<CustomerDTO, String> customersService;

    @Autowired
    private SmartFilteringService<CustomerDTO> customersSmartFilteringService;

    @Autowired
    private CustomValidator<FilterDTO> filterDtoValidator;

    @ApiOperation(value = "Get All customers REST API")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "number of element to be returned. Min value allowed: 1. Max value allowed 500. Default is 10", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "sort", value = "field to be sorted. value must be in [username,name,password,email,id]. Default is id", dataTypeClass = SortField.class, defaultValue = "id")})
    public ResponseEntity<List<CustomerDTO>> findAll(@Min(value = 1, message = "size min value is 1")
                                                     @Max(value = 500, message = "size max value is 500")
                                                     Integer size,
                                                     @RequestParam(defaultValue = "id")
                                                     SortField sort) {

        return ResponseEntity.ok().body(customersService.findAll(size, sort));
    }

    @ApiOperation(value = "Upsert customer REST API")
    @PostMapping
    public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO customerDto) {
        return ResponseEntity.ok(customersService.upsert(customerDto));
    }

    @ApiOperation(value = "Delete customer by id REST API")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") @NotNull String id) {
        customersService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Filter customers with different criteria API. " +
            "For field AGE operator must be: NOT_EQUALS, LESS_THAN, LESS_THAN_EQUALS, GREATER_THAN, GREATER_THAN_EQUALS." +
            "for fields USERNAME, NAME, ADDRESS, EMAIL operator must be: EQUALS, NOT_EQUALS, IN, NOT_IN, MATCHES")
    @PostMapping("/smartFiltering")
    public ResponseEntity<List<CustomerDTO>> smartFiltering(@Valid @RequestBody List<FilterDTO> filters) {
        List<ValidationError> errors = filters.stream()
                .map(filterDtoValidator::validate)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }
        return ResponseEntity.ok(customersSmartFilteringService.filterByCriteria(filters));
    }
}
