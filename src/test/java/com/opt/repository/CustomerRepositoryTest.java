package com.opt.repository;

import com.opt.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void deleteInBatch() {
    }

    @Test
    void getCustomerByStatus() {
    }

    @Test
    void getCustomerByAdmissionDate() {
    }

    @Test
    void findByCustomerName() {
    }


    @Test
    void saveMethod() {
        // create product

        Customer customer = new Customer();
        customer.setDescription("description");
        customer.setCustomerName("masum");
        customer.setStatus(Boolean.FALSE);
        customerRepository.save(customer);

        // save product
        Optional<Customer> customerfromdb = customerRepository.findById(Long.valueOf(1));

        // display product info
        assertThat(customerfromdb).isNotNull();

    }

    @Test
    void findByCustomerIdOrAccountNoOrDescription() {


    }
}