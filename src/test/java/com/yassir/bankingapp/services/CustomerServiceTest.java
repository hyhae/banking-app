package com.yassir.bankingapp.services;
import com.yassir.bankingapp.entities.Customer;
import com.yassir.bankingapp.repos.CustomerRepository;
import com.yassir.bankingapp.services.impl.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer_Successful() {
        Customer customer = new Customer();
        customer.setName("John Doe");

        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        Customer result = customerService.createCustomer("John Doe");

        assertEquals("John Doe", result.getName());

        Mockito.verify(customerRepository, Mockito.times(1)).save(Mockito.any(Customer.class));
    }

    @Test
    public void testGetCustomerByName_Successful() {
        Customer customer = new Customer();
        customer.setName("John Doe");

        when(customerRepository.findByName(anyString())).thenReturn(customer);

        Customer result = customerService.getCustomerByName("John Doe");

        assertEquals("John Doe", result.getName());

        Mockito.verify(customerRepository, Mockito.times(1)).findByName(anyString());
    }

}
