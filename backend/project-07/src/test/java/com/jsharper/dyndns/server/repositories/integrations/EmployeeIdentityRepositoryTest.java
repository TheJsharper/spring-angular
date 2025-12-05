package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.EmployeeIdentity;
import com.jsharper.dyndns.server.repositories.EmployeeIdentityRepository;
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
public class EmployeeIdentityRepositoryTest {

    @Autowired
    private EmployeeIdentityRepository employeeIdentityRepository;

    private final int countGeneratedEntities = 100;

    @BeforeEach
    void init() {
        employeeIdentityRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("create new Employee if provided valid employee employee and return employee entity with generated id automatically next")
    void createNewEmployee_whenProvidedValidInstanceEmployee_ReturnEmployeeGeneratedIdAutomatically() {
        //Arrange
        var employee = new EmployeeIdentity("Test FirstName", "Test LastName");

        //Act
        var storeEmployee = employeeIdentityRepository.save(employee);

        //Assert
        Assertions.assertTrue(storeEmployee.getId() > 0);
        Assertions.assertEquals(storeEmployee.getFirstName(), employee.getFirstName());
        Assertions.assertEquals(storeEmployee.getLastName(), employee.getLastName());

    }

    @TestFactory
    @Order(2)
    @DisplayName("")
    Stream<DynamicTest> createNewEmployeeEntities_whenProvidedValidListOfEmployeeEntities_returnsListOfEmployeeEntityIdGeneratedNext() {
        var iterable = getArguments().toList();

        var storedIterator = this.employeeIdentityRepository.saveAll(iterable);

        var storedStream = StreamSupport.stream(storedIterator.spliterator(), false);

        var pairs = StreamUtils.zip(iterable.stream(), storedStream, Pair::of);

        return pairs.map(e -> DynamicTest.dynamicTest(getEqualTwoProductEntities(e), assertEqualProductEntity(e)));
    }

    private Executable assertEqualProductEntity(Pair<EmployeeIdentity, EmployeeIdentity> e) {
        return () -> {
            assertEquals(e.getFirst().getFirstName(), e.getSecond().getFirstName());
            assertEquals(e.getFirst().getLastName(), e.getSecond().getLastName());
            // assertTrue(e.getFirst().getId() > 0);
        };
    }

    private String getEqualTwoProductEntities(Pair<EmployeeIdentity, EmployeeIdentity> e) {
        var breakLine = System.lineSeparator();
        return format("First FirstName %s second FirstName %s" + breakLine + " First LastName %s Second LastName %s" + breakLine + "First id %d Second id %d", e.getFirst().getFirstName(), e.getSecond().getFirstName(), e.getFirst().getLastName(), e.getSecond().getLastName(), e.getFirst().getId(), e.getSecond().getId());
    }

    private Stream<EmployeeIdentity> getArguments() {
        return IntStream.iterate(countGeneratedEntities, (n) -> n).limit(100).mapToObj((n) -> new EmployeeIdentity(" FirstName Test " + n, "LastName Test" + n));
    }

    @TestFactory
    @Order(3)
    @DisplayName("")
    Stream<DynamicTest> createNewEmployeeEntitiesAndCountId_whenProvidedValidListOfEmployeeInstance_returnListOfEmployeeIdGenerateNext() {

        Supplier<Stream<EmployeeIdentity>> iterable = this::getArguments;

        Supplier<Stream<EmployeeIdentity>> storedIterator = () -> StreamSupport.stream(this.employeeIdentityRepository.saveAll(iterable.get().toList()).spliterator(), false);


        var pairs = StreamUtils.zip(iterable.get(), storedIterator.get(), Pair::of);


        var stepOneStream = pairs.map(p -> DynamicTest.dynamicTest(getEqualTwoProductEntities(p), assertEqualProductEntity(p)));

        // this.employeeIdentityRepository.deleteAll();

        Supplier<Stream<Long>> ids = () -> storedIterator.get().map(EmployeeIdentity::getId);

        var sumIds = ids.get().reduce(0L, Long::sum);

        Supplier<Stream<Long>> expectedIds = () -> LongStream.rangeClosed(1L, 100L).boxed();

        var expectedSumIds = expectedIds.get().reduce(0L, Long::sum);

        var testSumIds = Stream.of(DynamicTest.dynamicTest(String.format("expected sum total ids %d actual sum total %d", expectedSumIds, sumIds), () -> assertEquals(expectedSumIds, sumIds)));

        var testIds = StreamUtils
                .zip(ids.get(), expectedIds.get(), Pair::of).map((p) -> DynamicTest.dynamicTest(String.format("expected value %d actual %d", p.getSecond(), p.getFirst()),
                        () -> assertEquals(p.getSecond(), p.getFirst())
                ));

        return Stream.concat(Stream.concat(stepOneStream, testSumIds), testIds);

    }
}
