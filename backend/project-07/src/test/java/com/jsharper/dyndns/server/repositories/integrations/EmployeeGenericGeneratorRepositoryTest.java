package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.EmployeeGenericGenerator;
import com.jsharper.dyndns.server.repositories.EmployeeGenericGeneratorRepository;
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

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeGenericGeneratorRepositoryTest {

    @Autowired
    private EmployeeGenericGeneratorRepository er;

    @Test
    @Order(1)
    @DisplayName("Create Two to test ids and distance by custom Generator")
    void createTwoEmployeeEntitiesByCustomGeneratorAnnotation_whenProvidedEntities_returnStoredEntities() {

        var employeeOne = new EmployeeGenericGenerator("Test FirstName One", "Test LastName One");
        var employeeTwo = new EmployeeGenericGenerator("Test FirstName Two", "Test LastName Two");

        var storedEmployeeOne = er.save(employeeOne);

        var storedEmployeeTwo = er.save(employeeTwo);

        Assertions.assertEquals(employeeOne.getFirstName(), storedEmployeeOne.getFirstName());
        Assertions.assertEquals(employeeOne.getLastName(), storedEmployeeOne.getLastName());
        Assertions.assertTrue(storedEmployeeOne.getId() > 0);

        Assertions.assertEquals(employeeTwo.getFirstName(), storedEmployeeTwo.getFirstName());
        Assertions.assertEquals(employeeTwo.getLastName(), storedEmployeeTwo.getLastName());
        Assertions.assertTrue(storedEmployeeTwo.getId() > 0);

        Assertions.assertEquals(149, storedEmployeeTwo.getId() - storedEmployeeOne.getId());

    }

    @TestFactory
    @Order(2)
    @DisplayName("Create new entities if provided valid list of employee entities return list of employee entities id generated next")
    Stream<DynamicTest> createNewEntities_whenProvidedValidListOfEmployeeInstance_returnListOfEmployeeIdGeneratorBy150() {

        var iterable = getArguments().toList();

        var storedIterator = this.er.saveAll(iterable);

        var storedStream = StreamSupport.stream(storedIterator.spliterator(), false);

        var pairs = StreamUtils.zip(iterable.stream(), storedStream, Pair::of);

        return pairs.map(e -> DynamicTest.dynamicTest(getEqualTwoProductEntities(e), assertEqualProductEntity(e)));

    }

    @TestFactory
    @Order(3)
    @DisplayName("check new if provided list of employee instances and return list of employee generator by distance ")
    Stream<DynamicTest> checkNewEntities_whenProvidedValidListOfEmployeeInstance_returnListOfEmployeeIdGeneratorBy150() {

        Supplier<Stream<EmployeeGenericGenerator>> iterable = this::getArguments;

        var iterator = this.er.saveAll(iterable.get().toList());


        Supplier<Stream<EmployeeGenericGenerator>> storedIterator = () -> StreamSupport.stream(iterator.spliterator(), false);


        var pairs = StreamUtils.zip(iterable.get(), storedIterator.get(), Pair::of);


        var stepOneStream = pairs.map(p -> DynamicTest.dynamicTest(getEqualTwoProductEntities(p), assertEqualProductEntity(p)));

        var values = storedIterator.get().toArray(EmployeeGenericGenerator[]::new);

        Supplier<LongStream> keys = () -> IntStream.range(0, values.length)

                .mapToLong(index -> index + 1 < values.length ? Math.abs(values[index + 1].getId()) - values[index].getId() : 9999)
                .filter(v -> v != 9999);


        var min = keys.get().min().orElseThrow();

        var max = keys.get().min().orElseThrow();


        var maxDynamicTest = DynamicTest.dynamicTest(String.format("Max generated id value is %d equals to %d", 1, max), () -> Assertions.assertEquals(150, max));

        var minDynamicTest = DynamicTest.dynamicTest(String.format("Min generated id value is %d equals to %d", 1, min), () -> Assertions.assertEquals(150, min));

        var stepTwo = Stream.of(maxDynamicTest, minDynamicTest);

        return Stream.concat(stepOneStream, stepTwo);
    }


    private Stream<EmployeeGenericGenerator> getArguments() {
        return IntStream.iterate(100, (n) -> n).limit(100).mapToObj((n) -> new EmployeeGenericGenerator(" FirstName Test " + n, "LastName Test " + n));
    }

    private Executable assertEqualProductEntity(Pair<EmployeeGenericGenerator, EmployeeGenericGenerator> e) {
        return () -> {
            assertEquals(e.getFirst().getFirstName(), e.getSecond().getFirstName());
            assertEquals(e.getFirst().getLastName(), e.getSecond().getLastName());
            // assertTrue(e.getFirst().getId() > 0);
        };
    }

    private String getEqualTwoProductEntities(Pair<EmployeeGenericGenerator, EmployeeGenericGenerator> e) {
        var breakLine = System.lineSeparator();
        return format("First FirstName %s second FirstName %s" + breakLine + " First LastName %s Second LastName %s" + breakLine + "First id %d Second id %d", e.getFirst().getFirstName(), e.getSecond().getFirstName(), e.getFirst().getLastName(), e.getSecond().getLastName(), e.getFirst().getId(), e.getSecond().getId());
    }

}
