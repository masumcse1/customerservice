package com.opt.repository;

import com.opt.entity.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itshouldsaveByPHone() {
        //Given
        Long id = 100L;
        String phoneNumber = "01728";
        Customer customer = new Customer(id, "masum", phoneNumber);

        //when
        underTest.save(customer);

        //then
        Optional<Customer> retrieveCustomer = underTest.findById(customer.getId());

        Customer retrieveCustomer1 = retrieveCustomer.get();
        assertEquals(retrieveCustomer1.getId(), id);
        assertEquals(retrieveCustomer1.getPhoneNumber(), phoneNumber);


    }
}