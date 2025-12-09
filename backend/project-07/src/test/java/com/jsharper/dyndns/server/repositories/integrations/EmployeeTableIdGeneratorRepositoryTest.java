package com.jsharper.dyndns.server.repositories.integrations;

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
