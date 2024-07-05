package com.opt.mapper;

import com.opt.dto.CustomerDto;
import com.opt.entity.Customer;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper {

    public Customer getEntityFromDto(CustomerDto dto){
        Customer customer  = new Customer();
        customer.setCustomerName(dto.getCustomerName());
        customer.setDescription(dto.getDescription());
        customer.setBalance(dto.getBalance());
        customer.setStatus(dto.getStatus());
        return customer;
    }


}
