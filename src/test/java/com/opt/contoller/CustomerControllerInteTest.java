package com.opt.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opt.dto.CustomerRegistrationRequest;
import com.opt.entity.Customer;
import com.opt.repository.CustomerRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerInteTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository  customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void itShouldregisterNewCustomerTest() throws Exception {
        // Given
        Long id = 300L;
        String phoneNumber = "54765612";
        Customer customer = new Customer();
        customer.setId(id);
        customer.setCustomerName("murad");
        customer.setPhoneNumber(phoneNumber);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);


        // When
        ResultActions response = mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Then - verify
        response.andDo(print()).andExpect(status().isOk());
    }

    @Disabled
    @Test
    void itshouldfindAllCustomerTest() throws Exception {

        //Given

        //shen
        ResultActions response = mockMvc.perform(get("/api/customers/list"));
        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.size()",
                        is(3)));

    }
}