package com.opt.integrationcontainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opt.dto.CustomerRegistrationRequest;
import com.opt.entity.Customer;
import com.opt.mapper.CustomerMapper;
import com.opt.service.ICustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CustomerControllerContainerTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ICustomerService customerService;

    @MockBean
    private CustomerMapper customerMapper;

    @Test
    void itShouldRegisterNewCustomerTest() throws Exception {
        // Given
        Long id = 100L;
        String phoneNumber = "01728";
        Customer customer = new Customer();
        customer.setId(id);
        customer.setCustomerName("MASUM");
        customer.setPhoneNumber(phoneNumber);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        willDoNothing().given(customerService).registerNewCustomer(any(CustomerRegistrationRequest.class));

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/customers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));

        // Then - verify
        response.andDo(print()).andExpect(status().isOk());
    }




    @Test
    void itShouldGetCustomerTest() throws Exception {
        // Given
        Long customerId = 100L;
        String phoneNumber = "01728";
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setCustomerName("MASUM");
        customer.setPhoneNumber(phoneNumber);

        given(customerService.findById(customerId)).willReturn(Optional.of(customer));

        // When - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/customers/{id}", customerId).contentType(MediaType.APPLICATION_JSON));

        // Then - verify
        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.data.customerName", is(customer.getCustomerName()))).andExpect(jsonPath("$.data.phoneNumber", is(customer.getPhoneNumber())));
    }


    @Test
    void itShouldGetCustomerNegativeTest() throws Exception {
        // Given
        Long customerId = 100L;
        given(customerService.findById(customerId)).willReturn(Optional.empty());

        // When - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/customers/cs/{id}", customerId).contentType(MediaType.APPLICATION_JSON));

        // Then - verify
        response.andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    void itshouldfindAllCustomerTest() throws Exception {

        //Given
        List<Customer> listOfcustomer = new ArrayList<>();
        listOfcustomer.add(new Customer(100l, "masum1", "0172827"));
        listOfcustomer.add(new Customer(101l, "masum2", "017245"));
        given(customerService.findAll()).willReturn(listOfcustomer);

        //shen
        ResultActions response = mockMvc.perform(get("/api/customers/list"));
        //then
        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.data.size()", is(listOfcustomer.size())));

    }

    @Test
    void itshouldCustomerDeletTest() throws Exception {

        // given - precondition or setup
        long customerid = 1L;
        Customer cs = new Customer(100l, "masum1", "0172827");
        given(customerService.findById(customerid)).willReturn(Optional.of(cs));
        willDoNothing().given(customerService).delete(any(Customer.class));
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/customers/{id}", customerid));

        // then - verify the output
        response.andExpect(status().isOk()).andDo(print());
    }


}