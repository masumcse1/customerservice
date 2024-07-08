package com.opt.contoller;

import com.opt.dto.CustomerDto;
import com.opt.dto.CustomerRegistrationRequest;
import com.opt.entity.Customer;
import com.opt.exception.NotFoundException;
import com.opt.mapper.RestResponse;
import com.opt.service.CustomerService;
import com.opt.service.ICustomerService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    private  MessageSource messageSource;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse getCustomer(@PathVariable(name = "id")  Long id) {
        Optional<Customer> customer = customerService.findById(id);
        return RestResponse.builder(customer.get()).build();
    }

    @RequestMapping(value = "/cs/{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer>  getCustomerXX(@PathVariable(name = "id")  Long id) {
      return   customerService.findById(id).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void registerNewCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.registerNewCustomer(request);
    }
   
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResponse saveCustomer(@RequestBody CustomerDto dto) {
        Customer customer = customerService.save(dto);
        return RestResponse.builder(customer).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestResponse update(@PathVariable(name = "id") Long id, @RequestBody CustomerDto dto) {
        Customer customer = customerService.update(id, dto);
        return RestResponse.builder(customer).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse delete(@PathVariable(name = "id") Long id) {
        Customer customer = customerService.findById(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("NOTFOUND", null, LocaleContextHolder.getLocale())));
        customerService.delete(customer);
        return RestResponse.builder(id).build();
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RestResponse  findAllCustomer(Customer customer){
        List<Customer> customers = customerService.findAll();
        return RestResponse.builder(customers).build();
    }

    @RequestMapping(value = "/{status}/customer/list", method = RequestMethod.GET)
    public  RestResponse getCustomersByStatus(@PathVariable Boolean status){
        List<Customer> customers =null;//= customerService.getCustomersByStatus(status);
        return RestResponse.builder(customers).build();
    }


}
