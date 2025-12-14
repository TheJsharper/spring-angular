package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.EmployeeAuto;
import com.jsharper.dyndns.server.entities.EmployeeIdentity;
import com.jsharper.dyndns.server.repositories.EmployeeAutoRepository;
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
public class EmployeeAutoRepositoryTest {

    @Autowired
    private EmployeeAutoRepository er;


    @Test
    @Order(1)
    @DisplayName("create new employeeAuto instance entity if provided employeeAuto entity instance return stored Employee Auto Entity")
    void createNewEmployee_whenProvidedNewEmployeeAutoEntity_returnStoredEmployeeAutoEntity() {
        //Arrange
        var employee = new EmployeeAuto("test FirstName ", "test LastName");

        //Act
        var storedEmployee = er.save(employee);

        //Assertion
        Assertions.assertEquals(employee.getFirstName(), storedEmployee.getFirstName());
        Assertions.assertEquals(employee.getLastName(), storedEmployee.getLastName());
        Assertions.assertTrue(storedEmployee.getId() > 0);
    }

    @TestFactory
    @Order(2)
    @DisplayName("create List Of New Employee if ProvidedNewEmployeeAuto Instance and then return StoreEntitiesOfEmployeeAuto")
    Stream<DynamicTest> createListOfNewEmployee_whenProvidedNewEmployeeAutoInstance_returnStoreEntitiesOfEmployeeAuto() {
        Supplier<Stream<EmployeeAuto>> supplier = this::getArguments;

        Supplier<Iterable<EmployeeAuto>> iterableSupplier = () -> er.saveAll(supplier.get().toList());

        Supplier<Stream<EmployeeAuto>> storeIterable = () -> StreamSupport.stream(iterableSupplier.get().spliterator(), false);

        var pairs = StreamUtils.zip(supplier.get(), storeIterable.get(), Pair::of);

        return pairs.map(e -> DynamicTest.dynamicTest(getEqualTwoProductEntities(e), assertEqualProductEntity(e)));

    }


    @TestFactory
    @Order(2)
    @DisplayName("create check List  Of New Employee if ProvidedNewEmployeeAuto Instance and then return StoreEntitiesOfEmployeeAuto")
    Stream<DynamicTest> createCheckIdsListOfNewEmployee_whenProvidedNewEmployeeAutoInstance_returnStoreEntitiesOfEmployeeAuto() {
        Supplier<Stream<EmployeeAuto>> supplier = this::getArguments;

        Supplier<Iterable<EmployeeAuto>> iterableSupplier = () -> er.saveAll(supplier.get().toList());

        Supplier<Stream<EmployeeAuto>> storedIterable = () -> StreamSupport.stream(iterableSupplier.get().spliterator(), false);

        var pairs = StreamUtils.zip(supplier.get(), storedIterable.get(), Pair::of);

        var stepOne = pairs.map(e -> DynamicTest.dynamicTest(getEqualTwoProductEntities(e), assertEqualProductEntity(e)));

        var values = storedIterable.get().toArray(EmployeeAuto[]::new);


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

    private Executable assertEqualProductEntity(Pair<EmployeeAuto, EmployeeAuto> e) {
        return () -> {
            assertEquals(e.getFirst().getFirstName(), e.getSecond().getFirstName());
            assertEquals(e.getFirst().getLastName(), e.getSecond().getLastName());
            // assertTrue(e.getFirst().getId() > 0);
        };
    }

    private String getEqualTwoProductEntities(Pair<EmployeeAuto, EmployeeAuto> e) {
        var breakLine = System.lineSeparator();
        return format("First FirstName %s second FirstName %s" + breakLine + " First LastName %s Second LastName %s" + breakLine + "First id %d Second id %d", e.getFirst().getFirstName(), e.getSecond().getFirstName(), e.getFirst().getLastName(), e.getSecond().getLastName(), e.getFirst().getId(), e.getSecond().getId());
    }

    private Stream<EmployeeAuto> getArguments() {
        return IntStream.iterate(1, (next) -> next++).limit(100)
                .mapToObj((index) -> new EmployeeAuto(" Test FirstName : " + index, " Test LastName: " + index));
    }
}
