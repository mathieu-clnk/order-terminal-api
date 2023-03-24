package com.kamvity.samples.otm.controller;

import com.kamvity.samples.cm.entity.Customer;
import com.kamvity.samples.cm.entity.ResponseCustomer;
import com.kamvity.samples.otm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseCustomer> getCustomerById(@RequestParam String id) {
        return customerService.getById(id);
    }

    @GetMapping("/get-by-email")
    public ResponseEntity<ResponseCustomer> getCustomerByEmail(@RequestParam String email) {
        return customerService.getByEmail(email);
    }

    @PostMapping(path = "/create-customer",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCustomer> createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }


}
