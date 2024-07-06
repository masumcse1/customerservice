package com.opt.service;

import com.opt.dto.CustomerRegistrationRequest;
import com.opt.entity.Customer;
import com.opt.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;


class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService undertest;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        undertest = new CustomerService(customerRepository);
    }

    @Test
    void itshouldSaveNewCustomer() {
        //Given
        Long id = 100L;
        String phoneNumber = "01728";
        Customer customer = new Customer(id, "masum", phoneNumber);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);
        //given(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).get
        when(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());
        //shen
        undertest.registerNewCustomer(request);

        //then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).isEqualTo(customer);


    }

    @Test
    void itShouldSaveNewCustomer() {

        // ... No customer with phone number passed
        given(customerRepository.selectCustomerByPhoneNumber("phoneNumber")).willReturn(Optional.empty());

        // When
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).isEqualTo(customer);
    }*/
}