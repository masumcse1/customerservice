package com.opt.service;

import com.opt.dto.CustomerDto;
import com.opt.entity.Customer;
import com.opt.mapper.CustomerMapper;
import com.opt.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;

    public Customer save(CustomerDto dto) {
        Customer customer = customerMapper.getEntityFromDto(dto);
        return customerRepository.save(customer);
    }

    public Customer update(Long id, CustomerDto customerDto) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        Customer customer = customerOpt.get();
        customer.setDescription(customerDto.getDescription());
        return customerRepository.save(customer);
    }

    public void delete(Customer customer) {

        customerRepository.delete(customer);
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }


    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> getCustomersByStatus(Boolean status) {
        return customerRepository.getCustomerByStatus(status);
    }


}
