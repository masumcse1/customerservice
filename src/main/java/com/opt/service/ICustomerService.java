package com.opt.service;

import com.opt.dto.CustomerDto;
import com.opt.dto.CustomerRegistrationRequest;
import com.opt.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    public void registerNewCustomer(CustomerRegistrationRequest request);
    public Customer save(CustomerDto dto);
    public Customer update(Long id, CustomerDto customerDto);
    public void delete(Customer customer);
    public Optional<Customer> findById(Long id);
    public List<Customer> findAll();
    public List<Customer> getCustomersByStatus(Boolean status);
}
