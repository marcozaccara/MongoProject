package mongo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import mongo.dto.CustomerDTO;
import mongo.dto.FilterDTO;
import mongo.enumerator.SortField;
import mongo.service.CRUDService;
import mongo.service.SmartFilteringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/customers")
@Api(value = "CRUD Rest APIs for customers")
@Validated
public class CustomersController {

    @Autowired
    private CRUDService<CustomerDTO> customersService;

    @Autowired
    private SmartFilteringService<CustomerDTO> customersSmartFilteringService;

    @ApiOperation(value = "Get All customers REST API")
    @GetMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "number of page. Min value allowed: 0. Max value allowed 500. Default is 10", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "size dimension. Min value allowed: 0. Max value allowed 500. Default is 10", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "sort", value = "field to be sorted. value must be in [username,name,password,email,id]. Default is id", dataTypeClass = SortField.class, defaultValue = "id")})
    public ResponseEntity<List<CustomerDTO>> findAll(@Min(value = 0, message = "page min value is 0")
                                                     @Max(value = 0, message = "page max value is 500") Integer page,
                                                     @Min(value = 0, message = "size min value is 0")
                                                     @Max(value = 0, message = "size max value is 500")
                                                     Integer size,
                                                     @RequestParam(defaultValue = "id")
                                                     SortField sort) {

        return ResponseEntity.ok().body(customersService.findAll(page, size, sort));
    }

    @ApiOperation(value = "Upsert customer REST API")
    @PostMapping
    public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO customerDto) {
        return ResponseEntity.ok(customersService.upsert(customerDto));
    }

    @ApiOperation(value = "Delete customer by id REST API")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {
        customersService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Filter customers with different criteria API")
    @PostMapping("/smartFiltering")
    public ResponseEntity<List<CustomerDTO>> smartFiltering(@Valid @RequestBody List<FilterDTO> filters) {
        return ResponseEntity.ok(customersSmartFilteringService.filterByCriteria(filters));
    }
}
