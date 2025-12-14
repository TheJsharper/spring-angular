package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.EmployeeSequence;
import com.jsharper.dyndns.server.repositories.EmployeeSequenceRepository;
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
public class EmployeeSequenceRepositoryTest {

    @Autowired
    private EmployeeSequenceRepository er;


    @Test
    @Order(1)
    @DisplayName("create new employeeSequence instance entity if provided employeeSequence entity instance return stored Employee Sequence Entity")
    void createNewEmployee_whenProvidedNewEmployeeSequenceEntity_returnStoredEmployeeSequenceEntity() {
        //Arrange
        var employee = new EmployeeSequence("test FirstName ", "test LastName");

        //Act
        var storedEmployee = er.save(employee);

        //Assertion
        Assertions.assertEquals(employee.getFirstName(), storedEmployee.getFirstName());
        Assertions.assertEquals(employee.getLastName(), storedEmployee.getLastName());
        Assertions.assertTrue(storedEmployee.getId() > 0);
    }

    @TestFactory
    @Order(2)
    @DisplayName("create List Of New Employee if ProvidedNewEmployeeSequence Instance and then return StoreEntitiesOfEmployeeSequence")
    Stream<DynamicTest> createListOfNewEmployee_whenProvidedNewEmployeeSequenceInstance_returnStoreEntitiesOfEmployeeSequence() {
        Supplier<Stream<EmployeeSequence>> supplier = this::getArguments;

        Supplier<Iterable<EmployeeSequence>> iterableSupplier = () -> er.saveAll(supplier.get().toList());

        Supplier<Stream<EmployeeSequence>> storeIterable = () -> StreamSupport.stream(iterableSupplier.get().spliterator(), false);

        var pairs = StreamUtils.zip(supplier.get(), storeIterable.get(), Pair::of);

        return pairs.map(e -> DynamicTest.dynamicTest(getEqualTwoProductEntities(e), assertEqualProductEntity(e)));

    }


    @TestFactory
    @Order(2)
    @DisplayName("create check List  Of New Employee if ProvidedNewEmployeeSequence Instance and then return StoreEntitiesOfEmployeeSequence")
    Stream<DynamicTest> createCheckIdsListOfNewEmployee_whenProvidedNewEmployeeSequenceInstance_returnStoreEntitiesOfEmployeeSequence() {
        Supplier<Stream<EmployeeSequence>> supplier = this::getArguments;

        Supplier<Iterable<EmployeeSequence>> iterableSupplier = () -> er.saveAll(supplier.get().toList());

        Supplier<Stream<EmployeeSequence>> storedIterable = () -> StreamSupport.stream(iterableSupplier.get().spliterator(), false);

        var pairs = StreamUtils.zip(supplier.get(), storedIterable.get(), Pair::of);

        var stepOne = pairs.map(e -> DynamicTest.dynamicTest(getEqualTwoProductEntities(e), assertEqualProductEntity(e)));

        var values = storedIterable.get().toArray(EmployeeSequence[]::new);


        Supplier<LongStream> keys = () -> IntStream.range(0, values.length)

                .mapToLong(index -> index + 1 < values.length ? Math.abs(values[index + 1].getId()) - values[index].getId() : 9999)
                .filter(v -> v != 9999);

        var min = keys.get().min().orElseThrow();

        var max = keys.get().min().orElseThrow();


        var maxDynamicTest = DynamicTest.dynamicTest(String.format("Max generated id value is %d equals to %d", 1, max), () -> Assertions.assertEquals(1, max));

        var minDynamicTest = DynamicTest.dynamicTest(String.format("Min generated id value is %d equals to %d", 1, min), () -> Assertions.assertEquals(1, min));

        var stepTwo = Stream.of(maxDynamicTest, minDynamicTest);

        return Stream.concat(stepOne, stepTwo);

    }

    private Executable assertEqualProductEntity(Pair<EmployeeSequence, EmployeeSequence> e) {
        return () -> {
            assertEquals(e.getFirst().getFirstName(), e.getSecond().getFirstName());
            assertEquals(e.getFirst().getLastName(), e.getSecond().getLastName());
            // assertTrue(e.getFirst().getId() > 0);
        };
    }

    private String getEqualTwoProductEntities(Pair<EmployeeSequence, EmployeeSequence> e) {
        var breakLine = System.lineSeparator();
        return format("First FirstName %s second FirstName %s" + breakLine + " First LastName %s Second LastName %s" + breakLine + "First id %d Second id %d", e.getFirst().getFirstName(), e.getSecond().getFirstName(), e.getFirst().getLastName(), e.getSecond().getLastName(), e.getFirst().getId(), e.getSecond().getId());
    }

    private Stream<EmployeeSequence> getArguments() {
        return IntStream.iterate(1, (next) -> next++).limit(100)
                .mapToObj((index) -> new EmployeeSequence(" Test FirstName : " + index, " Test LastName: " + index));
    }
}
