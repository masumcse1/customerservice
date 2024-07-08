package com.opt.service;

import com.opt.dto.CustomerDto;
import com.opt.dto.CustomerRegistrationRequest;
import com.opt.entity.Customer;
import com.opt.mapper.CustomerMapper;
import com.opt.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService implements ICustomerService {

    private  CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void registerNewCustomer(CustomerRegistrationRequest request) {
        String phoneNumber = request.getCustomer().getPhoneNumber();

        Optional<Customer> customerOptional = customerRepository.selectCustomerByPhoneNumber(phoneNumber);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (customer.getCustomerName().equals(request.getCustomer().getCustomerName())) {
                return;
            }
            throw new IllegalStateException(String.format("phone number [%s] is taken", phoneNumber));
        }

        if(request.getCustomer().getId() == null) {
            request.getCustomer().setId(200l);
        }

        customerRepository.save(request.getCustomer());
    }

    @Override
    public Customer save(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setCustomerName(dto.getCustomerName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Long id, CustomerDto customerDto) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        Customer customer = customerOpt.get();
        customer.setDescription(customerDto.getDescription());
        return customerRepository.save(customer);
    }
    @Override
    public void delete(Customer customer) {

        customerRepository.delete(customer);
    }
    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    @Override
    public List<Customer> getCustomersByStatus(Boolean status) {
        return customerRepository.getCustomerByStatus(status);
    }


}
