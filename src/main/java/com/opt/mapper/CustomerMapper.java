package com.opt.mapper;

import com.opt.dto.CustomerDto;
import com.opt.entity.Customer;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper {


    public Customer toCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customer;
    }

    public CustomerDto toCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setCustomerName(customer.getCustomerName());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }

}
