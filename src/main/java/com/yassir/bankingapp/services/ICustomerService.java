package com.yassir.bankingapp.services;

import com.yassir.bankingapp.entities.Customer;

public interface ICustomerService {
    Customer createCustomer(String name);

    Customer getCustomerByName(String holderName);
}
