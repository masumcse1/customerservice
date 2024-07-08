package com.opt.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opt.dto.CustomerDto;
import com.opt.dto.CustomerRegistrationRequest;
import com.opt.entity.Customer;
import com.opt.mapper.CustomerMapper;
import com.opt.service.ICustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Controller level mock test

@WebMvcTest
class CustomerControllerRealTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ICustomerService customerService;

    @MockBean
    private CustomerMapper customerMapper;



    @Test
    void itShouldregisterNewCustomerTest() throws Exception {
        // Given
        Long id = 100L;
        String phoneNumber = "01728";
        Customer customer = new Customer();
        customer.setId(id);
        customer.setCustomerName("MASUM");
        customer.setPhoneNumber(phoneNumber);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        willDoNothing().given(customerService).registerNewCustomer(any(CustomerRegistrationRequest.class));
        //given(customerService.registerNewCustomer(any(CustomerRegistrationRequest.class))).willReturn(customer);//Error
        //given(customerService.registerNewCustomer(any(CustomerRegistrationRequest.class))).willAnswer((invocation) -> invocation.getArgument(0)); // Error

        // When
        ResultActions response = mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Then - verify
        response.andDo(print()).andExpect(status().isOk());
    }


    @Test
    void itShouldsaveCustomerTest() throws Exception {
        // Given
        Long id = 100L;
        String phoneNumber = "01728";
        Customer customer = new Customer();
        customer.setId(id);
        customer.setCustomerName("MASUM");
        customer.setPhoneNumber(phoneNumber);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setCustomerName(customer.getCustomerName());
        customerDto.setPhoneNumber(customer.getPhoneNumber());

        given(customerService.save(any(CustomerDto.class))).willReturn(customer);
        //given(customerService.save(any(CustomerDto.class))).willAnswer((invocation) -> invocation.getArgument(0)); // Error
        // When
        ResultActions response = mockMvc.perform(post("/api/customers/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)));

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
        ResultActions response = mockMvc.perform(get("/api/customers/{id}", customerId)
                .contentType(MediaType.APPLICATION_JSON));

        // Then - verify
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.customerName", is(customer.getCustomerName())))
                .andExpect(jsonPath("$.data.phoneNumber", is(customer.getPhoneNumber())));
    }


    @Test
    void itShouldGetCustomerNegativeTest() throws Exception {
        // Given
        Long customerId = 100L;
        given(customerService.findById(customerId)).willReturn(Optional.empty());

        // When - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/customers/cs/{id}", customerId)
                .contentType(MediaType.APPLICATION_JSON));

        // Then - verify
        response.andExpect(status().isNotFound())
                .andDo(print());
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
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.size()",
                        is(listOfcustomer.size())));

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

    @Test
    void updateTest() throws Exception {
        // Given - precondition or setup
        long customerId = 1L;
        Customer existingCustomer = new Customer(100L, "masum1", "0172827");
        Customer updatedCustomer = new Customer(100L, "masum_updated", "0172827");
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(100L);
        customerDto.setCustomerName("masum_updated");
        customerDto.setPhoneNumber("0172827");

        //given(customerService.findById(customerId)).willReturn(Optional.of(existingCustomer));
        given(customerService.update(eq(customerId), any(CustomerDto.class))).willReturn(updatedCustomer);

        // When - action or behavior that we are going to test
        ResultActions response = mockMvc.perform(put("/api/customers/{id}", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)));

        // Then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.customerName", is(updatedCustomer.getCustomerName())))
                .andExpect(jsonPath("$.data.phoneNumber", is(updatedCustomer.getPhoneNumber())));







    }
}