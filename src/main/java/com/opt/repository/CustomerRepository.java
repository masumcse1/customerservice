package com.opt.repository;

import com.opt.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  // @Query(value = "select * from customer where phone_number = :phoneNumber",nativeQuery = true)
 // @Query(value = "select id, customerName, phone_number from customer where phone_number = :phoneNumber", nativeQuery = true ) // Error critical
  @Query(value = "select * from customer where phone_number = :phoneNumber",nativeQuery = true)
    Optional<Customer> selectCustomerByPhoneNumber(@Param("phoneNumber") String phoneNumber);
    List<Customer> getCustomerByStatus(Boolean status);
    List<Customer> getCustomerByAdmissionDate(LocalDate admissionDate);
    Optional<Customer> findByCustomerName(String customerName);
    Optional<Customer> readByPhoneNumber(String customerName);
   // Page<Customer> findByCustomerIdOrAccountNoOrDescription(String customerId, String accountNo, String description, Pageable pageable);


}
