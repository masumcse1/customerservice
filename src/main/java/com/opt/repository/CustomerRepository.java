package com.opt.repository;

import com.opt.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> getCustomerByStatus(Boolean status);
    List<Customer> getCustomerByAdmissionDate(LocalDate admissionDate);
    Optional<Customer> findByCustomerName(String customerName);
   // Page<Customer> findByCustomerIdOrAccountNoOrDescription(String customerId, String accountNo, String description, Pageable pageable);


}
