package mongo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import mongo.dto.CustomerDTO;
import mongo.dto.FilterDTO;
import mongo.dto.QueryParamDTO;
import mongo.service.CRUDService;
import mongo.service.CustomersSmartFilteringServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/customers")
@Api(value = "CRUD Rest APIs for customers")
@Validated
public class CustomersController {

    @Autowired
    private CRUDService<CustomerDTO> customersService;

    @Autowired
    private CustomersSmartFilteringServiceImpl customersSmartFilteringService;

    @ApiOperation(value = "Get All customers REST API")
    @GetMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "number of page. Min value allowed: 0. Max value allowed 500. Default is 10", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "size dimension. Min value allowed: 0. Max value allowed 500. Default is 10", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "sort", value = "field to be sorted. value must be in [username,name,password,email]. Default is _id", dataTypeClass = String.class)})
    public ResponseEntity<List<CustomerDTO>> findAll(@Valid QueryParamDTO queryParams) {

        return ResponseEntity.ok().body(customersService.findAll(queryParams.getPage(), queryParams.getSize(), queryParams.getSort()));
    }

    @ApiOperation(value = "Upsert customer REST API")
    @PostMapping
    public ResponseEntity<CustomerDTO> save(@RequestBody CustomerDTO customerDto) {
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
    public ResponseEntity<List<CustomerDTO>> smartFiltering(@RequestBody List<FilterDTO> filters) {
        return ResponseEntity.ok(customersSmartFilteringService.filterByCriteria(filters));
    }
}
