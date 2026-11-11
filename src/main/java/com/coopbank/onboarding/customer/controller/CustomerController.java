package com.coopbank.onboarding.customer.controller;

import com.coopbank.onboarding.customer.dto.RegisterCustomerRequest;
import com.coopbank.onboarding.customer.entity.Customer;
import com.coopbank.onboarding.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public Customer registerCustomer(

            @Valid
            @RequestBody
            RegisterCustomerRequest request

    ) {

        return customerService
                .registerCustomer(
                        request
                );
    }
}