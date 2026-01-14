package com.jsharper.dyndns.server.repositories.fetch;

import com.jsharper.dyndns.server.entities.fetch.CustomerFetch;
import com.jsharper.dyndns.server.entities.fetch.PhoneFetch;
import jakarta.persistence.Tuple;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;

import java.util.Comparator;
import java.util.HashSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
    Stream<DynamicTest> createCustomerWithChildrenAndChildrenSavedParentFetchPersist_providedCustomerAndChildrenPhoneFetch_returnEntityCustomer() {

        var phones = getPhones();

        var customer = new CustomerFetch("Test Name", phones);

        phones = phones.stream().peek(p -> p.setCustomerFetch(customer)).collect(Collectors.toCollection(HashSet::new));

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
                            Assertions.assertEquals(storedCustomer.getId(), second.getCustomerFetchId());
                        }
                )
        );

    }

    @TestFactory
    @Order(2)
    Stream<DynamicTest> createCustomerWithChildrenAndChildrenSavedParentFetchPersist_providedCustomerAndChildrenPhoneFetch_returnEntityCustomerFetchEagerly() {

        var phones = getPhones();

        var customer = new CustomerFetch("Test Name", phones);

        phones = phones.stream().peek(p -> p.setCustomerFetch(customer)).collect(Collectors.toCollection(HashSet::new));

        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);

        Supplier<Stream<PhoneFetch>> supplier = () -> storedCustomer.getPhones().stream().sorted(Comparator.comparing(PhoneFetch::getId));


        var pairs = StreamUtils.zip(phones.stream(), supplier.get().sorted(Comparator.comparing(PhoneFetch::getId)), Pair::of);

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
                        }
                )
        );

    }


    @TestFactory
    @Order(3)
    Stream<DynamicTest> createCustomerWithChildrenAndChildrenSavedParentFetchPersistUpdateParent_providedCustomerAndChildrenPhoneFetch_returnEntityCustomerFetchEagerly() {

        var updateName = "Update Name";

        var phones = getPhones();

        var customer = new CustomerFetch("Test Name", phones);

        phones = phones.stream().peek(p -> p.setCustomerFetch(customer)).collect(Collectors.toCollection(HashSet::new));

        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);

        storedCustomer.setName(updateName);

        var updatedCustomer = cr.save(storedCustomer);

        Assertions.assertEquals(customer.getId(), updatedCustomer.getId());

        Assertions.assertEquals(customer.getName(), updatedCustomer.getName());

        Assertions.assertEquals(storedCustomer.getId(), updatedCustomer.getId());

        Assertions.assertEquals(storedCustomer.getName(), updatedCustomer.getName());

        Assertions.assertEquals(updateName, updatedCustomer.getName());

        Supplier<Stream<PhoneFetch>> supplier = () -> storedCustomer.getPhones().stream().sorted(Comparator.comparing(PhoneFetch::getId));


        var pairs = StreamUtils.zip(phones.stream(), supplier.get().sorted(Comparator.comparing(PhoneFetch::getId)), Pair::of);

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
                        }
                )
        );

    }

    @TestFactory
    @Order(4)
    Stream<DynamicTest> createCustomerWithChildrenAndChildrenSavedParentFetchPersistUpdateParentAndChildren_providedCustomerAndChildrenPhoneFetch_returnEntityCustomerFetchEagerly() {

        var update = "Update";

        var phones = getPhones();

        var customer = new CustomerFetch("Test Name", phones);

        phones = phones.stream().peek(p -> p.setCustomerFetch(customer)).collect(Collectors.toCollection(HashSet::new));

        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);

        var phonesUpdate = phones.stream().map(p -> new PhoneFetch(p.getId(), p.getNumber() + update, p.getType() + update))
                .collect(Collectors.toCollection(HashSet::new));

        var updateCustomer = cr.save(new CustomerFetch(storedCustomer.getId(), storedCustomer.getName(), phonesUpdate));

        Assertions.assertEquals(updateCustomer.getId(), storedCustomer.getId());

        Assertions.assertEquals(updateCustomer.getName(), storedCustomer.getName());

        Supplier<Stream<PhoneFetch>> supplier = () -> updateCustomer.getPhones().stream().sorted(Comparator.comparing(PhoneFetch::getId));


        var pairs = StreamUtils.zip(storedCustomer.getPhones().stream(), supplier.get().sorted(Comparator.comparing(PhoneFetch::getId)), Pair::of);

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
                            Assertions.assertEquals(first.getNumber() + update, second.getNumber());
                            Assertions.assertEquals(first.getType() + update, second.getType());
                        }
                )
        );

    }

    private static @NotNull HashSet<PhoneFetch> getPhones() {
        var phones = new HashSet<PhoneFetch>();


        PhoneFetch phoneFetch = new PhoneFetch("12225566", "mobile");
        phones.add(phoneFetch);

        PhoneFetch phoneFetch1 = new PhoneFetch("0585846565", "mobile");
        phones.add(phoneFetch1);

        PhoneFetch phoneFetch2 = new PhoneFetch("101010101", "pickup");
        phones.add(phoneFetch2);
        return phones;
    }
}
