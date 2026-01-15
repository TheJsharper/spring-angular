package com.jsharper.dyndns.server.repositories.fetch;

import com.jsharper.dyndns.server.entities.fetch.CustomerFetch;
import com.jsharper.dyndns.server.entities.fetch.PhoneFetch;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;

import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Stream;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PhoneFetchRepositoryTest {

    @Autowired
    private PhoneFetchRepository pr;

    @Autowired
    private CustomerFetchRepository cr;

    @AfterEach
    @BeforeEach
    public void cleanUp() {

        cr.deleteAll();
        Assertions.assertEquals(0, cr.count());
        pr.deleteAll();
        Assertions.assertEquals(0, pr.count());

    }

    @Test
    @Order(1)
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

    @TestFactory
    @Order(2)
    Stream<DynamicTest> createPhoneUsingParentRepository_ProvidedPhoneInstanceWithParentRelatedInstance_returnPhoneEntityWithParentRelation() {
        var phone = new PhoneFetch("+5955458585", "mobile");

        var phones = new HashSet<PhoneFetch>();

        phones.add(phone);

        var customer = new CustomerFetch("TestName", phones);

        phone.setCustomerFetch(customer);

        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);
        Assertions.assertEquals(storedCustomer.getId(), customer.getId());
        Assertions.assertEquals(storedCustomer.getName(), customer.getName());

        var pairs = StreamUtils.zip(phones.stream().sorted(Comparator.comparing(PhoneFetch::getId)), storedCustomer.getPhones().stream().sorted(Comparator.comparing(PhoneFetch::getId)), Pair::of);

        return pairs.map((p) -> DynamicTest.dynamicTest(String.format("first(id:%d, number:%s, type:%s) second(id:%d, number:%s, type:%s)", p.getFirst().getId(), p.getFirst().getNumber(), p.getFirst().getType(), p.getSecond().getId(), p.getSecond().getNumber(), p.getSecond().getType()), () -> {
            var first = p.getFirst();
            var second = p.getSecond();
            System.out.println(first);
            System.out.println(second);
            Assertions.assertTrue(second.getId() > 0);
            Assertions.assertEquals(second.getId(), first.getId());
            Assertions.assertEquals(first.getNumber(), second.getNumber());
            Assertions.assertEquals(first.getType(), second.getType());
        }));

    }
}
