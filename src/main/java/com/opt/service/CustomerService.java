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
public class CustomerService {


    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
   // @Autowired
  //  private CustomerMapper customerMapper;

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

    public Customer save(CustomerDto dto) {
      //  Customer customer = customerMapper.getEntityFromDto(dto);
      //  return customerRepository.save(customer);
        return null;
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
