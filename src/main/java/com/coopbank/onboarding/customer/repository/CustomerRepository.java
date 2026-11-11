package com.coopbank.onboarding.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coopbank.onboarding.customer.entity.Customer;

import com.coopbank.onboarding.customer.entity.Customer;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    Optional<Customer>
    findByPhoneNumber(String phoneNumber);

    Optional<Customer>
    findByNationalId(String nationalId);
}