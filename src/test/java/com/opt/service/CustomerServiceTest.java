package com.opt.service;

import com.opt.dto.CustomerRegistrationRequest;
import com.opt.entity.Customer;
import com.opt.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
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
        //Given
        when(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());

        undertest.registerNewCustomer(request);

        //then
        //verify
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).isEqualTo(customer);
    }

    @Test
    void itshouldSaveNewCustomerWithIdNull() {
        //Given
        Long id = 100L;
        String phoneNumber = "01728";
        Customer rscustomer = new Customer(null, "masum", phoneNumber);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(rscustomer);
        //Given
        when(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());
        undertest.registerNewCustomer(request);
        //then
        //verify
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).isEqualTo(rscustomer);
        Assertions.assertThat(customerArgumentCaptorValue).isEqualToIgnoringGivenFields(rscustomer, "customerName");
        Assertions.assertThat(customerArgumentCaptorValue.getId()).isNotNull();
        Assertions.assertThat(rscustomer.getId()).isNotNull();
    }


    @Test
    void itShouldNotSaveCustomerWhenCustomerExists() {

        //Given
        Long id = 100L;
        String phoneNumber = "01728";
        Customer rscustomer = new Customer(null, "masum", phoneNumber);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(rscustomer);
        //Given
        when(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).thenReturn(Optional.of(rscustomer));
        undertest.registerNewCustomer(request);
        //then
        then(customerRepository).should(never()).save(any());
        then(customerRepository).should(never()).save(any());

    }

    @Test
    void itShouldThrowWhenPhoneNumberIsTaken() {

        //Given
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(200L, "Maryam", phoneNumber);
        Customer customerTwo = new Customer(300L, "John", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... No customer with phone number passed
        when(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).thenReturn(Optional.of(customerTwo));
        //shen


        //then
        assertThatThrownBy(() -> undertest.registerNewCustomer(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("phone number [%s] is taken", phoneNumber));

    }
}