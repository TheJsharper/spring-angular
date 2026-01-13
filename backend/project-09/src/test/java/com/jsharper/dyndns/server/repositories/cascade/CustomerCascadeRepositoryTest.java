package com.jsharper.dyndns.server.repositories.cascade;

import com.jsharper.dyndns.server.entities.cascade.CustomerCascade;
import com.jsharper.dyndns.server.entities.cascade.PhoneCascade;
import com.jsharper.dyndns.server.repository.cascade.CustomerCascadeRepository;
import com.jsharper.dyndns.server.repository.cascade.PhoneCascadeRepository;
import jakarta.persistence.Tuple;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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
public class CustomerCascadeRepositoryTest {

    @Autowired
    private CustomerCascadeRepository cr;

    @Autowired
    private PhoneCascadeRepository pr;

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
        var phones = new HashSet<PhoneCascade>();


        phones.add(new PhoneCascade("12225566", "mobile"));

        phones.add(new PhoneCascade("0585846565", "mobile"));

        phones.add(new PhoneCascade("101010101", "pickup"));

        var customer = new CustomerCascade("Test Name", phones);


        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);

        Supplier<Stream<Tuple>> supplier = () -> cr.getRelationsResult().stream();

        var storedPhones = supplier.get().map(t ->
                new PhoneCascade(
                        t.get("id", Long.class),
                        t.get("number", String.class),
                        t.get("type", String.class),
                        t.get("customer_cascade_id", CustomerCascade.class)
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
                            Assertions.assertEquals(first.getCustomerCascade(), second.getCustomerCascade());
                            Assertions.assertNull(second.getCustomerCascade());
                            Assertions.assertNull(first.getCustomerCascade());
                        }
                )
        );

    }


    @TestFactory
    @Order(2)
    Stream<DynamicTest> createCustomerWithChildrenAndChildrenSavedParentCascadePersist_providedCustomerAndChildrenPhoneCascade_returnEntityCustomer() {
        var phones = new HashSet<PhoneCascade>();


        PhoneCascade phoneCascade = new PhoneCascade("12225566", "mobile");

        phones.add(phoneCascade);

        PhoneCascade phoneCascade1 = new PhoneCascade("0585846565", "mobile");

        phones.add(phoneCascade1);

        PhoneCascade phoneCascade2 = new PhoneCascade("101010101", "pickup");

        phones.add(phoneCascade2);

        var customer = new CustomerCascade("Test Name", phones);

        phoneCascade.setCustomerCascade(customer);

        phoneCascade1.setCustomerCascade(customer);

        phoneCascade2.setCustomerCascade(customer);

        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);

        Supplier<Stream<Tuple>> supplier = () -> cr.getRelationsResult().stream();

        var storedPhones = supplier.get().map(t ->
                new PhoneCascade(
                        t.get("id", Long.class),
                        t.get("number", String.class),
                        t.get("type", String.class),
                        t.get("customer_cascade_id", Long.class)
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
                            Assertions.assertEquals(storedCustomer.getId(), second.getCustomerCascadeId());
                        }
                )
        );

    }

    @Test
    @Order(3)
    void createCustomerWithChildrenAndChildrenSavedParentCascadePersistAndRemoveManagedEntity_providedCustomerAndChildrenPhoneCascade_returnThrowsExceptionConstraintViolation() {
        var phones = new HashSet<PhoneCascade>();


        PhoneCascade phoneCascade = new PhoneCascade("12225566", "mobile");

        phones.add(phoneCascade);

        PhoneCascade phoneCascade1 = new PhoneCascade("0585846565", "mobile");

        phones.add(phoneCascade1);

        PhoneCascade phoneCascade2 = new PhoneCascade("101010101", "pickup");

        phones.add(phoneCascade2);

        var customer = new CustomerCascade("Test Name", phones);

        phoneCascade.setCustomerCascade(customer);

        phoneCascade1.setCustomerCascade(customer);

        phoneCascade2.setCustomerCascade(customer);

        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);

        var exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> cr.deleteById(storedCustomer.getId()));

        MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.containsString("could not execute statement [Referential integrity constraint violation:"));


    }
}
