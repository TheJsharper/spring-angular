package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.Customer;
import com.jsharper.dyndns.server.entities.Phone;
import com.jsharper.dyndns.server.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository cr;

    @Test
    void test(){
        var phones = new HashSet<Phone>();
        phones.add(new Phone("12225566", "mobile"));
        phones.add(new Phone("0585846565", "mobile"));
        phones.add(new Phone("101010101", "pickup"));
        var customer = new Customer("Test Name", phones);
        var storedCustomer = cr.save(customer);
        System.out.println(storedCustomer);

        Assertions.assertEquals(storedCustomer, customer);
    }
}
