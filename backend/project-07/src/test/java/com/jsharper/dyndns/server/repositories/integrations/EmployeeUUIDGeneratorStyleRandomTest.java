package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.uuid.generators.EmployeeUUIDGeneratorStyleRandom;
import com.jsharper.dyndns.server.entities.uuid.generators.EmployeeUUIDGeneratorStyleTime;
import com.jsharper.dyndns.server.repositories.EmployeeUUIDGeneratorRandomRepository;
import com.jsharper.dyndns.server.repositories.EmployeeUUIDGeneratorStyleTimeRepository;
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
public class EmployeeUUIDGeneratorStyleRandomTest {

    @Autowired
    private EmployeeUUIDGeneratorRandomRepository er;

    @Test
    @Order(1)
    @DisplayName("create new EmployeeUUIDGeneratorStyleRandom instance entity if provided EmployeeUUIDGeneratorStyleRandom entity instance return stored Employee StyleUUID Entity")
    void createNewUUIDGenerator_whenProvidedUUIDGenerator_returnStoreUUIDGenerator() {
        var employee = new EmployeeUUIDGeneratorStyleRandom("Test FirstName", "Test LastName");

        var storedEmployee = er.save(employee);

        Assertions.assertEquals(employee.getFirstName(), storedEmployee.getFirstName());
        Assertions.assertEquals(employee.getLastName(), storedEmployee.getLastName());
        Assertions.assertNotNull(employee.getId());
        Assertions.assertInstanceOf(UUID.class, employee.getId());
        Assertions.assertEquals(4, employee.getId().version());
    }

    @TestFactory
    @Order(2)
    @DisplayName("create List Of New Employee if EmployeeUUIDGeneratorStyleRandom Instance and then return StoreEntitiesOfUUIDGenerator")
    Stream<DynamicTest> createListOfNewEmployee_whenProvidedNewUUIDGeneratorInstance_returnStoreEntitiesOfUUIDGenerator() {
        Supplier<Stream<EmployeeUUIDGeneratorStyleRandom>> supplier = this::getArguments;

        Supplier<Iterable<EmployeeUUIDGeneratorStyleRandom>> iterableSupplier = () -> er.saveAll(supplier.get().toList());

        Supplier<Stream<EmployeeUUIDGeneratorStyleRandom>> storeIterable = () -> StreamSupport.stream(iterableSupplier.get().spliterator(), false);

        var pairs = StreamUtils.zip(supplier.get(), storeIterable.get(), Pair::of);

        return pairs.map(e -> DynamicTest.dynamicTest(getEqualTwoProductEntities(e), assertEqualProductEntity(e)));

    }

    private Executable assertEqualProductEntity(Pair<@Nullable EmployeeUUIDGeneratorStyleRandom, @Nullable EmployeeUUIDGeneratorStyleRandom> e) {
        return () -> {
            assertEquals(e.getFirst().getFirstName(), e.getSecond().getFirstName());
            assertEquals(e.getFirst().getLastName(), e.getSecond().getLastName());
            Assertions.assertInstanceOf(UUID.class, e.getSecond().getId());
            Assertions.assertEquals(4, e.getSecond().getId().version());
        };
    }

    private String getEqualTwoProductEntities(Pair<EmployeeUUIDGeneratorStyleRandom, EmployeeUUIDGeneratorStyleRandom> e) {
        var breakLine = System.lineSeparator();
        return format("First FirstName %s second FirstName %s" + breakLine + " First LastName %s Second LastName %s" + breakLine + "First id %s Second id %s", e.getFirst().getFirstName(), e.getSecond().getFirstName(), e.getFirst().getLastName(), e.getSecond().getLastName(), e.getFirst().getId(), e.getSecond().getId());
    }

    private Stream<EmployeeUUIDGeneratorStyleRandom> getArguments() {
        return IntStream.iterate(1, (next) -> next++).limit(100)
                .mapToObj((index) -> new EmployeeUUIDGeneratorStyleRandom(" Test FirstName : " + index, " Test LastName: " + index));
    }
}
