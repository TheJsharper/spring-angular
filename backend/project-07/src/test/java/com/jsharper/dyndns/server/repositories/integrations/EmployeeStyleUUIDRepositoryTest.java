package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.EmployeeStyleUUID;
import com.jsharper.dyndns.server.repositories.EmployeeStyleUUIDRepository;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;
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
public class EmployeeStyleUUIDRepositoryTest {

    @Autowired
    private EmployeeStyleUUIDRepository er;

    @Test
    @Order(1)
    @DisplayName("create new employeeStyleUUID instance entity if provided employeeStyleUUID entity instance return stored Employee StyleUUID Entity")
    void createNewEmployeeStyleUUID_whenProvidedEmployeeStyleUUID_returnStoreEmployeeStyleUUID() {
        var employee = new EmployeeStyleUUID("Test FirstName", "Test LastName");

        var storedEmployee = er.save(employee);

        Assertions.assertEquals(employee.getFirstName(), storedEmployee.getFirstName());
        Assertions.assertEquals(employee.getLastName(), storedEmployee.getLastName());
        Assertions.assertNotNull(storedEmployee.getId());
        Assertions.assertInstanceOf(UUID.class,  storedEmployee.getId());
        System.out.println(employee+"\n"+ storedEmployee);
    }

    @TestFactory
    @Order(2)
    @DisplayName("create List Of New Employee if ProvidedNewEmployeeStyleUUID Instance and then return StoreEntitiesOfEmployeeStyleUUID")
    Stream<DynamicTest> createListOfNewEmployee_whenProvidedNewEmployeeStyleUUIDInstance_returnStoreEntitiesOfEmployeeStyleUUID() {
        Supplier<Stream<EmployeeStyleUUID>> supplier = this::getArguments;

        Supplier<Iterable<EmployeeStyleUUID>> iterableSupplier = () -> er.saveAll(supplier.get().toList());

        Supplier<Stream<EmployeeStyleUUID>> storeIterable = () -> StreamSupport.stream(iterableSupplier.get().spliterator(), false);

        var pairs = StreamUtils.zip(supplier.get(), storeIterable.get(), Pair::of);

        return pairs.map(e -> DynamicTest.dynamicTest(getEqualTwoProductEntities(e), assertEqualProductEntity(e)));

    }

    private Executable assertEqualProductEntity(Pair< @Nullable  EmployeeStyleUUID, @Nullable EmployeeStyleUUID> e) {
        return () -> {
            assertEquals(e.getFirst().getFirstName(), e.getSecond().getFirstName());
            assertEquals(e.getFirst().getLastName(), e.getSecond().getLastName());
            // assertTrue(e.getFirst().getId() > 0);
        };
    }

    private String getEqualTwoProductEntities(Pair<EmployeeStyleUUID, EmployeeStyleUUID> e) {
        var breakLine = System.lineSeparator();
        return format("First FirstName %s second FirstName %s" + breakLine + " First LastName %s Second LastName %s" + breakLine + "First id %s Second id %s", e.getFirst().getFirstName(), e.getSecond().getFirstName(), e.getFirst().getLastName(), e.getSecond().getLastName(), e.getFirst().getId(), e.getSecond().getId());
    }

    private Stream<EmployeeStyleUUID> getArguments() {
        return IntStream.iterate(1, (next) -> next++).limit(100)
                .mapToObj((index) -> new EmployeeStyleUUID(" Test FirstName : " + index, " Test LastName: " + index));
    }
}
