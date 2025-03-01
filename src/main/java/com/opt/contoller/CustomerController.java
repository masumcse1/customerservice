package com.opt.contoller;

import com.opt.dto.CustomerDto;
import com.opt.dto.CustomerRegistrationRequest;
import com.opt.dto.MessageConstant;
import com.opt.entity.Customer;
import com.opt.exception.NotFoundException;
import com.opt.mapper.RestResponse;
import com.opt.service.CustomerService;
import com.opt.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(
        name = "CRUD REST APIs for Customer Resource",
        description = "Customer REST APIs - Create User, Update User, Get User, Get All Users, Delete Customer"
)
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    private  MessageSource messageSource;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  MessageConstant.GET_PAGED_OBJECT, content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))}),
            @ApiResponse(responseCode = "400", description =  MessageConstant.BAD_REQUEST_MSG,  content = @Content),
            @ApiResponse(responseCode = "500", description =  MessageConstant.INTERNAL_SERVER_ERROR_MSG, content = @Content)})
    @Parameter(in = ParameterIn.QUERY, name = "id", schema =@Schema(type = "string", example = "acct_m0CB9LflojZxytfC"), required =false)
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
    @Operation(
            summary = "Create Customer REST API",
            description = " save customer in a database"
    )
    @ApiResponse(
            responseCode = "201",
            content = { @Content(mediaType = "application/json", schema =@Schema(implementation = CustomerDto.class))},
            description = "HTTP Status 201 CREATED"
    )
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
/*
    @RequestMapping(value = "/{id}/paymentmethod", method = RequestMethod.GET)
    public RestResponse getPaymentMethodByType(@PathVariable("id") String id, @RequestParam("type") PaymentMethodType type)throws CardException, AuthenticationException, StripeException, InvalidRequestException  {
        List<PaymentMethodModel> paymentMethods = customerService.getPaymentMethodByType(id, type);
        return RestResponse.builder(paymentMethods).build();
    }


       @RequestMapping(value = "/{id}/paymentmethod", method = RequestMethod.POST)
    public RestResponse addPaymentMethod(@PathVariable("id") String id, @Valid @RequestBody PaymentMethodForm form)throws CardException, AuthenticationException, StripeException, InvalidRequestException  {
        PaymentMethodModel paymentMethodModel = customerService.addPaymentMethod(id, form);
        return RestResponse.builder(paymentMethodModel).build();
    }


    */



}
