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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CustomerControllerRealTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICustomerService customerService;

    @MockBean
    private CustomerMapper customerMapper;

    @Autowired
    private ObjectMapper objectMapper;

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

}