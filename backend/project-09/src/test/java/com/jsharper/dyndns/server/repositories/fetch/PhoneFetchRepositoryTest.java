package com.jsharper.dyndns.server.repositories.fetch;

import com.jsharper.dyndns.server.entities.fetch.CustomerFetch;
import com.jsharper.dyndns.server.entities.fetch.PhoneFetch;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.HashSet;

@SpringBootTest
public class PhoneFetchRepositoryTest {

    @Autowired
    private PhoneFetchRepository pr;

    @Autowired
    private CustomerFetchRepository cr;

    @AfterEach
    public void cleanUp() {
        cr.deleteAll();
        Assertions.assertEquals(0, cr.count());
        pr.deleteAll();
        Assertions.assertEquals(0, pr.count());
    }

    @Test
    void createPhoneTryToSaveExpectedException_ProvidedPhoneInstanceWithParentRelatedInstance_returnExceptionInvalidDataAccess() {
        var phone = new PhoneFetch("+5955458585", "mobile");

        var phones = new HashSet<PhoneFetch>();

        phones.add(phone);

        var customer = new CustomerFetch("TestName", phones);

        phone.setCustomerFetch(customer);

        var invalidDataAccessApiUsageException = Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> pr.save(phone));

        Matcher<String> matcher = CoreMatchers.containsString("references an unsaved transient instance of");

        MatcherAssert.assertThat(invalidDataAccessApiUsageException.getMessage(), matcher);


    }

    @Test
    void createPhoneUsingParentRepository_ProvidedPhoneInstanceWithParentRelatedInstance_returnPhoneEntityWithParentRelation() {
        var phone = new PhoneFetch("+5955458585", "mobile");

        var phones = new HashSet<PhoneFetch>();

        phones.add(phone);

        var customer = new CustomerFetch("TestName", phones);

        phone.setCustomerFetch(customer);

        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);
        /*var invalidDataAccessApiUsageException = Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> pr.save(phone));

        Matcher<String> matcher = CoreMatchers.containsString("references an unsaved transient instance of");

        MatcherAssert.assertThat(invalidDataAccessApiUsageException.getMessage(), matcher);*/


    }
}
