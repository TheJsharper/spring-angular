package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.Customer;
import com.jsharper.dyndns.server.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository cr;

    @Test
    void test(){
        var customer = new Customer("Test Name");
        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);
    }
}
