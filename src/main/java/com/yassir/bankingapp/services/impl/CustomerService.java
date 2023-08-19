package com.yassir.bankingapp.services.impl;

import com.yassir.bankingapp.entities.Customer;
import com.yassir.bankingapp.repos.CustomerRepository;
import com.yassir.bankingapp.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(String name){
        Customer customer = new Customer();
        customer.setName(name);
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByName(String holderName) {
        return customerRepository.findByName(holderName);
    }

}
