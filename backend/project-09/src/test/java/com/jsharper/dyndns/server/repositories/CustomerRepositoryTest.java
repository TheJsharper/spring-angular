package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.Customer;
import com.jsharper.dyndns.server.entities.Phone;
import com.jsharper.dyndns.server.repository.CustomerRepository;
import com.jsharper.dyndns.server.repository.PhoneRepository;
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
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository cr;

    @Autowired
    private PhoneRepository pr;


    @AfterEach
    public void cleanAfterTest() {
        cr.deleteAll();
        Assertions.assertEquals(0, cr.count());
        pr.deleteAll();
        Assertions.assertEquals(0, pr.count());
    }

    @TestFactory
    @DisplayName("create relations customer with phone instance checking database relations without saving parent object")
    @Order(1)
    Stream<DynamicTest> createRelationsCustomerWithPhoneInstanceCheckingDatabaseRelationshipWithoutChildrenParentId_providedCustomerEntitiesWithPhone_returnAllPhoneDatabaseTable() {
        var phones = new HashSet<Phone>();


        phones.add(new Phone("12225566", "mobile"));

        phones.add(new Phone("0585846565", "mobile"));

        phones.add(new Phone("101010101", "pickup"));

        var customer = new Customer("Test Name", phones);

        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);


        Supplier<Stream<Tuple>> supplier = () -> cr.getRelationsResult().stream();
        var storedPhones = supplier.get().map(t ->
                new Phone(
                        t.get("id", Long.class),
                        t.get("number", String.class),
                        t.get("type", String.class),
                        t.get("customer_id", Customer.class)
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
                            Assertions.assertEquals(first.getCustomer(), second.getCustomer());
                            Assertions.assertNull(second.getCustomer());
                            Assertions.assertNull(first.getCustomer());
                        }
                )
        );


    }

    @TestFactory
    @DisplayName("create relations customer with phone instance checking database relations with saving parent object")
    @Order(2)
    Stream<DynamicTest> createRelationsCustomerWithPhoneInstanceCheckingDatabaseRelationshipWithChildrenParentId_providedCustomerEntitiesWithPhone_returnAllPhoneDatabaseTable() {
        var phones = new HashSet<Phone>();


        Phone phone1 = new Phone("12225566", "mobile");
        phones.add(phone1);

        Phone phone2 = new Phone("0585846565", "mobile");
        phones.add(phone2);

        Phone phone3 = new Phone("101010101", "pickup");
        phones.add(phone3);

        var customer = new Customer("Test Name", phones);

        phone1.setCustomer(customer);
        phone2.setCustomer(customer);
        phone3.setCustomer(customer);

        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);


        Supplier<Stream<Tuple>> supplier = () -> cr.getRelationsResult().stream();

        var pairs = StreamUtils.zip(phones.stream(), supplier.get(), Pair::of);

        return pairs.map((p) -> DynamicTest.dynamicTest(
                        String.format("first(id:%d, number:%s, type:%s) second(id:%d, number:%s, type:%s)",
                                p.getFirst().getId(), p.getFirst().getNumber(), p.getFirst().getType(),
                                p.getSecond().get("id", Long.class), p.getSecond().get("number", String.class), p.getSecond().get("type", String.class)
                        ),
                        () -> {
                            var first = p.getFirst();
                            var second = p.getSecond();
                            Assertions.assertTrue(second.get("id", Long.class) > 0);
                            Assertions.assertEquals(second.get("id", Long.class), first.getId());
                            Assertions.assertEquals(first.getNumber(), second.get("number", String.class));
                            Assertions.assertEquals(first.getType(), second.get("type", String.class));
                            Assertions.assertTrue(second.get("customer_id", Integer.class) > 0);
                            Assertions.assertEquals(second.get("customer_id", Integer.class), storedCustomer.getId());
                        }
                )
        );


    }


    @TestFactory
    @DisplayName("create relations customer with phone instance checking database relations with saving parent object")
    @Order(3)
    Stream<DynamicTest> createRelationsCustomerWithPhoneInstanceCheckingDatabaseRelationshipWithChildrenParentId_providedCustomerEntitiesWithPhone2_returnAllPhoneDatabaseTable() {
        var phones = new HashSet<Phone>();


        Phone phone1 = new Phone("12225566", "mobile");
        phones.add(phone1);

        Phone phone2 = new Phone("0585846565", "mobile");
        phones.add(phone2);

        Phone phone3 = new Phone("101010101", "pickup");
        phones.add(phone3);

        var customer = new Customer("Test Name", phones);

        phone1.setCustomer(customer);
        phone2.setCustomer(customer);
        phone3.setCustomer(customer);

        var storedCustomer = cr.save(customer);

        Assertions.assertEquals(storedCustomer, customer);

        var foundCustomer = cr.findById(storedCustomer.getId()).orElseThrow();

        Assertions.assertEquals( storedCustomer.getId(), foundCustomer.getId());


        Supplier<Stream<Phone>> supplier = () -> foundCustomer.getPhones().stream();

        var pairs = StreamUtils.zip(phones.stream(), supplier.get(), Pair::of);

        return pairs.map((p) -> DynamicTest.dynamicTest(
                        String.format("first(id:%d, number:%s, type:%s) second(id:%d, number:%s, type:%s)",
                                p.getFirst().getId(), p.getFirst().getNumber(), p.getFirst().getType(),
                                p.getSecond().getId(), p.getSecond().getNumber(), p.getSecond().getType()
                        ),
                        () -> {
                            var first = p.getFirst();
                            var second = p.getSecond();
                            Assertions.assertTrue(second.getId() > 0);
                            Assertions.assertEquals(first.getNumber(), second.getNumber());
                            Assertions.assertEquals(first.getType(), second.getType());
                            Assertions.assertEquals(first.getCustomer().getId(), foundCustomer.getId());
                        }
                )
        );


    }


}
