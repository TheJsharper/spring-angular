package com.jsharper.dyndns.server.repositories.fetch;

import com.jsharper.dyndns.server.entities.cascade.CustomerCascade;
import com.jsharper.dyndns.server.entities.cascade.PhoneCascade;
import com.jsharper.dyndns.server.entities.fetch.CustomerFetch;
import com.jsharper.dyndns.server.entities.fetch.PhoneFetch;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerFetchRepositoryTest {

    @Autowired
    private CustomerFetchRepository cr;

    @Autowired
    private PhoneFetchRepository pr;

    @AfterEach
    void cleanUpAfterEach() {
        pr.deleteAll();
        Assertions.assertEquals(0, pr.count());
        cr.deleteAll();
        Assertions.assertEquals(0, cr.count());
    }


    @TestFactory
    @Order(1)
    Stream<DynamicTest> createCustomerWithChildrenAndChildrenUnsavedParentCascadePersist_providedCustomerAndChildrenPhoneCascade_returnEntityCustomer() {
        var phones = new HashSet<PhoneFetch>();


        PhoneFetch phoneFetch = new PhoneFetch("12225566", "mobile");
        phones.add(phoneFetch);

        PhoneFetch phoneFetch1 = new PhoneFetch("0585846565", "mobile");
        phones.add(phoneFetch1);

        PhoneFetch phoneFetch2 = new PhoneFetch("101010101", "pickup");
        phones.add(phoneFetch2);

        var customer = new CustomerFetch("Test Name", phones);

        phoneFetch.setCustomerFetch(customer);

        phoneFetch1.setCustomerFetch(customer);

        phoneFetch2.setCustomerFetch(customer);

        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);

        Supplier<Stream<Tuple>> supplier = () -> cr.getRelationsResult().stream();

        var storedPhones = supplier.get().map(t ->
                new PhoneFetch(
                        t.get("id", Long.class),
                        t.get("number", String.class),
                        t.get("type", String.class),
                        t.get("customer_fetch_id", Long.class)
                )
        );

        var pairs = StreamUtils.zip(phones.stream(), storedPhones, Pair::of);

        return pairs.map((p) -> DynamicTest.dynamicTest(
                        String.format("first(id:%d, number:%s, type:%s) second(id:%d, number:%s, type:%s)",
                                p.getFirst().getId(), p.getFirst().getNumber(), p.getFirst().getType(),
                                p.getSecond().getId(), p.getSecond().getNumber(), p.getSecond().getType()
                        ),
                        () -> {
                            var first = p.getFirst();
                            var second = p.getSecond();

                            Assertions.assertTrue(second.getId() > 0);
                            Assertions.assertEquals(second.getId(), first.getId());
                            Assertions.assertEquals(first.getNumber(), second.getNumber());
                            Assertions.assertEquals(first.getType(), second.getType());
                            Assertions.assertEquals(storedCustomer.getId(),second.getCustomerFetchId());
                        }
                )
        );

    }


}
