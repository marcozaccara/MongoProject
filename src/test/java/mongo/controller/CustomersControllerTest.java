package mongo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mongo.dto.CustomerDTO;
import mongo.dto.FilterDTO;
import mongo.enumerator.FieldEnum;
import mongo.enumerator.OperatorEnum;
import mongo.enumerator.SortField;
import mongo.service.CRUDService;
import mongo.service.SmartFilteringService;
import mongo.validator.CustomValidator;
import mongo.validator.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.Constants.CUSTOMERS_BASE_URL;
import static utils.Constants.STRING;

@WebMvcTest(CustomersController.class)
@ContextConfiguration(classes = {CustomersController.class, ControllerAdvisor.class, CRUDService.class, SmartFilteringService.class, CustomValidator.class})
class CustomersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CRUDService<CustomerDTO> customersService;
    @MockBean
    private SmartFilteringService<CustomerDTO> customersSmartFilteringService;
    @MockBean
    private CustomValidator<FilterDTO> filterDtoValidator;

    @Test
    @DisplayName("Given the controller, When findAll API is called with default parameters, then response is status 200")
    void testFindAllOk() throws Exception {
        when(customersService.findAll(10, SortField.id)).thenReturn(new ArrayList<>());
        mockMvc.perform(get(CUSTOMERS_BASE_URL))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Given the controller, When findAll API is called with size parameter not valid, then response is status 400")
    void testFindAllKoValidationSize() throws Exception {
        mockMvc.perform(get(CUSTOMERS_BASE_URL + "/?size=501"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Given the controller, When findAll API is called with sort parameter not valid, then response is status 500")
    void testFindAllKoValidationSort() throws Exception {
        mockMvc.perform(get(CUSTOMERS_BASE_URL + "/?sort=string"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Given the controller, When upsert API is called with generic body, then response is status 200")
    void testUpsertOk() throws Exception {
        CustomerDTO body = CustomerDTO.builder().age(20).username(STRING).email(STRING).address(STRING).id(STRING).name(STRING).birthDate(LocalDateTime.of(2020, 1, 1, 0, 0)).build();
        when(customersService.upsert(body)).thenReturn(body);
        mockMvc.perform(post(CUSTOMERS_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(body)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Given the controller, When upsert API is called with null body, then response is status 500")
    void testUpsertKo() throws Exception {
        mockMvc.perform(post(CUSTOMERS_BASE_URL)
                        .content(new ObjectMapper().writeValueAsString(null)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Given the controller, When upsert API is called with body with wrong fields, then response is status 400")
    void testUpsertValidationtKo() throws Exception {
        CustomerDTO body = CustomerDTO.builder().age(20).username(null).email("").build();
        mockMvc.perform(post(CUSTOMERS_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(body)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Given the controller, When delete API is called, then response is status 204")
    void testDeleteOk() throws Exception {
        mockMvc.perform(delete(CUSTOMERS_BASE_URL + "/{id}", "0"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Given the controller, When smartFiltering API is called, then response is status 200")
    void testSmartFilteringOk() throws Exception {
        FilterDTO filterDto = FilterDTO.builder().field(FieldEnum.AGE).value(STRING).operator(OperatorEnum.GREATER_THAN).build();
        List<FilterDTO> body = new ArrayList<>();
        body.add(filterDto);
        when(customersSmartFilteringService.filterByCriteria(body)).thenReturn(new ArrayList<>());
        mockMvc.perform(post(CUSTOMERS_BASE_URL + "/smartFiltering")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Given the controller, When smartFiltering API is called with wrong body, then response is status 400")
    void testSmartFilteringValidationKo() throws Exception {
        FilterDTO filterDto = FilterDTO.builder().field(FieldEnum.NAME).value(STRING).operator(OperatorEnum.GREATER_THAN).build();
        List<FilterDTO> body = new ArrayList<>();
        body.add(filterDto);
        when(filterDtoValidator.validate(filterDto)).thenReturn(Collections.singletonList(ValidationError.builder().build()));
        mockMvc.perform(post(CUSTOMERS_BASE_URL + "/smartFiltering")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(body)))
                .andExpect(status().is4xxClientError());
    }

}