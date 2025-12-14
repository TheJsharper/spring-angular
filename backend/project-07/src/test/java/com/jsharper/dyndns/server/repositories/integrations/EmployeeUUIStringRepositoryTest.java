package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.EmployeeSequence;
import com.jsharper.dyndns.server.entities.EmployeeUUIString;
import com.jsharper.dyndns.server.repositories.EmployeeUUIStringRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeUUIStringRepositoryTest {

    @Autowired
    private EmployeeUUIStringRepository er;


    @Test
    @Order(1)
    @DisplayName("create new employeeUUIString instance entity if provided employeeUUIString entity instance return stored Employee UUIString Entity")
    void createNewEmployeeUUIString_whenProvidedEmployeeUUIString_returnStoredEmployeeUUIString() {
        var employee = new EmployeeUUIString("Test FirstName", "Test LastName");

        var storedEmployee = er.save(employee);


        Assertions.assertEquals(employee.getFirstName(), storedEmployee.getFirstName());
        Assertions.assertEquals(employee.getLastName(), storedEmployee.getLastName());
        Assertions.assertNotNull(employee.getId());
        Assertions.assertFalse(employee.getId().isEmpty());


    }

    @TestFactory
    @Order(2)
    @DisplayName("create List Of New Employee if ProvidedNewEmployeeSequence Instance and then return StoreEntitiesOfEmployeeSequence")
    Stream<DynamicTest> createListOfNewEmployee_whenProvidedNewEmployeeSequenceInstance_returnStoreEntitiesOfEmployeeSequence() {
        Supplier<Stream<EmployeeUUIString>> supplier = this::getArguments;

        Supplier<Iterable<EmployeeUUIString>> iterableSupplier = () -> er.saveAll(supplier.get().toList());

        Supplier<Stream<EmployeeUUIString>> storeIterable = () -> StreamSupport.stream(iterableSupplier.get().spliterator(), false);

        var pairs = StreamUtils.zip(supplier.get(), storeIterable.get(), Pair::of);

        return pairs.map(e -> DynamicTest.dynamicTest(getEqualTwoProductEntities(e), assertEqualProductEntity(e)));

    }

    private Executable assertEqualProductEntity(Pair<EmployeeUUIString, EmployeeUUIString> e) {
        return () -> {
            assertEquals(e.getFirst().getFirstName(), e.getSecond().getFirstName());
            assertEquals(e.getFirst().getLastName(), e.getSecond().getLastName());
            // assertTrue(e.getFirst().getId() > 0);
        };
    }

    private String getEqualTwoProductEntities(Pair<EmployeeUUIString, EmployeeUUIString> e) {
        var breakLine = System.lineSeparator();
        return format("First FirstName %s second FirstName %s" + breakLine + " First LastName %s Second LastName %s" + breakLine + "First id %s Second id %s", e.getFirst().getFirstName(), e.getSecond().getFirstName(), e.getFirst().getLastName(), e.getSecond().getLastName(), e.getFirst().getId(), e.getSecond().getId());
    }

    private Stream<EmployeeUUIString> getArguments() {
        return IntStream.iterate(1, (next) -> next++).limit(100)
                .mapToObj((index) -> new EmployeeUUIString(" Test FirstName : " + index, " Test LastName: " + index));
    }
}
