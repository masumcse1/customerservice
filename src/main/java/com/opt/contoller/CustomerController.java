package com.opt.contoller;

import com.opt.dto.CustomerDto;
import com.opt.entity.Customer;
import com.opt.exception.NotFoundException;
import com.opt.mapper.RestResponse;
import com.opt.service.CustomerService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    private  MessageSource messageSource;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestResponse save(@RequestBody CustomerDto dto) {
        Customer customer = customerService.save(dto);
        return RestResponse.builder(customer).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestResponse update(@PathVariable Long id, @RequestBody CustomerDto dto) {
        Customer customer = customerService.update(id, dto);
        return RestResponse.builder(customer).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse delete(@PathVariable Long id) {
        Customer customer = customerService.findById(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("NOTFOUND", null, LocaleContextHolder.getLocale())));
        customerService.delete(customer);
        return RestResponse.builder(id).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse getCustomer(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        return RestResponse.builder(customer).build();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RestResponse  findAllCustomer(Customer customer){
        List<Customer> customers = customerService.findAll();
        return RestResponse.builder(customers).build();
    }

    @RequestMapping(value = "/{status}/customer/list", method = RequestMethod.GET)
    public  RestResponse getCustomersByStatus(@PathVariable Boolean status){
        List<Customer> customers = customerService.getCustomersByStatus(status);
        return RestResponse.builder(customers).build();
    }


}
