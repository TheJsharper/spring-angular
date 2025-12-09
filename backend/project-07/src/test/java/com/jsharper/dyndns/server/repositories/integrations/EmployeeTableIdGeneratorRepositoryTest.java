package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.EmployeeIdentity;
import com.jsharper.dyndns.server.entities.EmployeeTableIdGenerator;
import com.jsharper.dyndns.server.repositories.EmployeeTableIdGeneratorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeTableIdGeneratorRepositoryTest {

    @Autowired
    EmployeeTableIdGeneratorRepository er;

    @Test
    @Order(1)
    @DisplayName("create employee table id generator entities if provided one instance entity return stored entity")
    void createEmployeeTableIdGeneratorRepository_whenProvidedValueEmployeeEntities_returnStoredEntity() {
        //Arrange
        var employee = new EmployeeTableIdGenerator("Test FirstName", "Test LastName");
        //Act
        var storeEmployee = er.save(employee);

        //Assert
        Assertions.assertEquals(storeEmployee.getFirstName(), employee.getFirstName());
        Assertions.assertEquals(storeEmployee.getLastName(), employee.getLastName());
        Assertions.assertNotNull(storeEmployee.getId());
        Assertions.assertTrue(storeEmployee.getId() >= 1);


    }

    @TestFactory
    @Order(2)
    @DisplayName("create employee table id generator entities if provided list of entities return stored entities")
    Stream<DynamicTest> createEmployeeTableIdGeneratorEntities_whenProvidedEmployeeEntities_returnStoredEntitiesIterable() {

        //Arrange
        Supplier<Stream<EmployeeTableIdGenerator>> entities = this::getArguments;

        //Act
        var storeEntities = er.saveAll(entities.get().toList());

        var stream = StreamSupport.stream(storeEntities.spliterator(), false);

        var pairs = StreamUtils.zip(entities.get(), stream, Pair::of);
        //Asserts
        return pairs.map(e -> DynamicTest.dynamicTest(getEqualTwoProductEntities(e), assertEqualProductEntity(e)));


    }

    @TestFactory
    @Order(3)
    @DisplayName("check id generate and create employee table id generator entities if provided list of entities return stored entities ")
    Stream<DynamicTest> checkAndCreateEmployeeTableIdGeneratorEntities_whenProvidedEmployeeEntities_returnStoredEntitiesIterable() {

        //Arrange
        Supplier<Stream<EmployeeTableIdGenerator>> entities = this::getArguments;

        //Act
        Supplier<Iterable<EmployeeTableIdGenerator>> storeEntities = () -> er.saveAll(entities.get().toList());

        Supplier<Stream<EmployeeTableIdGenerator>> stream = () -> StreamSupport.stream(storeEntities.get().spliterator(), false);

        var pairs = StreamUtils.zip(entities.get(), stream.get(), Pair::of);

        //Assert
        var stepOneStream = pairs.map(p -> DynamicTest.dynamicTest(getEqualTwoProductEntities(p), assertEqualProductEntity(p)));


        var values = stream.get().toArray(EmployeeTableIdGenerator[]::new);


        Supplier<LongStream> keys = () -> IntStream.range(0, values.length)

                .mapToLong(index -> index + 1 < values.length ? Math.abs(values[index + 1].getId()) - values[index].getId() : 9999)
                .filter(v -> v != 9999);

        var min = keys.get().min().orElseThrow();

        var max = keys.get().min().orElseThrow();

        var maxDynamicTest = DynamicTest.dynamicTest(String.format("Max generated id value is %d equals to %d", 1, max), () -> Assertions.assertEquals(1, max));

        var minDynamicTest = DynamicTest.dynamicTest(String.format("Min generated id value is %d equals to %d", 1, min), () -> Assertions.assertEquals(1, min));

        var stepTwo = Stream.of(maxDynamicTest, minDynamicTest);
        //Asserts
        return Stream.concat(stepOneStream, stepTwo);
    }

    private Executable assertEqualProductEntity(Pair<EmployeeTableIdGenerator, EmployeeTableIdGenerator> e) {
        return () -> {
            assertEquals(e.getFirst().getFirstName(), e.getSecond().getFirstName());
            assertEquals(e.getFirst().getLastName(), e.getSecond().getLastName());
            assertTrue(e.getSecond().getId() > 0);
        };
    }

    private String getEqualTwoProductEntities(Pair<EmployeeTableIdGenerator, EmployeeTableIdGenerator> e) {
        var breakLine = System.lineSeparator();
        return format("First FirstName %s second FirstName %s" + breakLine + " First LastName %s Second LastName %s" + breakLine + "First id %d Second id %d", e.getFirst().getFirstName(), e.getSecond().getFirstName(), e.getFirst().getLastName(), e.getSecond().getLastName(), e.getFirst().getId(), e.getSecond().getId());
    }

    private Stream<EmployeeTableIdGenerator> getArguments() {
        return IntStream.iterate(100, (n) -> n).limit(100).mapToObj((n) -> new EmployeeTableIdGenerator(" FirstName Test " + n, "LastName Test" + n));
    }
}
