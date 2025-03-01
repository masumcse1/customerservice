package com.opt.repository;

import com.opt.entity.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

    @Test
    void itshouldSaveTest2() {

        //Given
        Long id = 100L;
        String phoneNumber = "01728";
        Customer customer = new Customer(id, "masum", phoneNumber);

        //when
        underTest.save(customer);
        //then

        Optional<Customer> retrieveCS = underTest.findById(customer.getId());
        assertThat(retrieveCS).isPresent().hasValueSatisfying(c -> {

            assertThat(c.getCustomerId()).isEqualTo(customer.getCustomerId());
            assertThat(c).isEqualTo(customer);
            assertThat(c).isEqualToComparingFieldByField(customer);

        });

    }

    @Test
    void itshouldPHoneNonotExists() {

        //Given
        String phoneNumber = "01728";

        //shen
        Optional<Customer> customer = underTest.readByPhoneNumber(phoneNumber);

        //then
        // assertThat(customer).isPresent();
        assertThat(customer).isNotPresent();

    }


    @Test
    void phonenovalidTest() {

        //Given
        Long id = 100L;
        String phoneNumber = "01728";
        Customer customer = new Customer(id, "masum", null);

        //when
        //then

        assertThatThrownBy(()->underTest.save(customer))
                .hasMessageContaining("not-null property references a null or transient value : com.opt.entity.Customer.phoneNumber")
                .isInstanceOf(DataIntegrityViolationException.class);


    }


}