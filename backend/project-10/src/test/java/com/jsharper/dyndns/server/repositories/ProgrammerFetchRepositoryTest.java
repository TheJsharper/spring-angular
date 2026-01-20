package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.fetch.ProgrammerFetch;
import com.jsharper.dyndns.server.entities.fetch.ProjectFetch;
import com.jsharper.dyndns.server.repositories.fetch.ProgrammerFetchRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProgrammerFetchRepositoryTest {

    @Autowired
    private ProgrammerFetchRepository pr;


    private Long id;

    @AfterAll
    public void cleanUp() {
        pr.deleteAll();
        Assertions.assertEquals(0, pr.count());
    }


    @Test
    @Order(1)
    void test() {
        var programmer = new ProgrammerFetch("Test FirstName", "Test LastName", 2500);

        var storedProgrammer = pr.save(programmer);

        Assertions.assertEquals(programmer, storedProgrammer);
    }

    @Test
    @Order(2)
    void createProgrammerInstanceWithRelatedProjectInstance_ProvidedProgrammerInstanceWithRelatedProject_returnStoredEntityWithItsRelation() {
        var projects = new HashSet<ProjectFetch>();

        var project = new ProjectFetch("Spring Framework");

        projects.add(project);

        var programmer = new ProgrammerFetch("Test FirstName", "Test LastName", 2500, projects);

        var storedProgrammer = pr.save(programmer);

        this.id = programmer.getId();

        Assertions.assertEquals(programmer, storedProgrammer);

        Assertions.assertEquals(1, storedProgrammer.getProjects().size());


    }

    @Test
    @Order(3)
    void findProgrammerById_ProvidedIdFromPreviousTest_returnExceptionLazyInitializationException() {
        Assertions.assertNotNull(this.id, "You should run previous test Order(2)");

        var found = pr.findById(this.id).orElseThrow(() -> new ArithmeticException("No Found Programmer!!!"));

        var failureMessage = "Cannot lazily initialize collection of role";

        Assertions.assertEquals(found.getId(), this.id);

        var exception = Assertions.assertThrows(LazyInitializationException.class, () -> found.getProjects().forEach(System.out::println));

        Matcher<String> matcher = CoreMatchers.containsString(failureMessage);

        MatcherAssert.assertThat(exception.getMessage(), matcher);


    }

    @Test
    @Order(4)
    void findProgrammerByIdAndUpdate_ProvidedIdFromPreviousTest_returnUpdatedEntity() {

        Assertions.assertNotNull(this.id, "You should run previous test Order(2)");

        var found = pr.findById(this.id).orElseThrow(() -> new ArithmeticException("No Found Programmer!!!"));

        String firstNameUpdated = "FirstName updated";

        String lastNameUpdated = "LastName updated";

        int sal = 5800;

        found.setFirstName(firstNameUpdated);

        found.setLastName(lastNameUpdated);

        found.setSal(sal);

        var updateProgrammer = pr.save(found);

        Assertions.assertEquals(found.getId(), updateProgrammer.getId());
        Assertions.assertEquals(found.getFirstName(), updateProgrammer.getFirstName());
        Assertions.assertEquals(found.getLastName(), updateProgrammer.getLastName());

        Assertions.assertEquals(firstNameUpdated, updateProgrammer.getFirstName());
        Assertions.assertEquals(lastNameUpdated, updateProgrammer.getLastName());
        Assertions.assertEquals(sal, updateProgrammer.getSal());


    }


    @Test
    @Order(5)
    void findProgrammerByIdAndDelete_ProvidedIdFromPreviousTest_returnCheckingFindById() {

        Assertions.assertNotNull(this.id, "You should run previous test Order(2)");

        var found = pr.findById(this.id).orElseThrow(() -> new ArithmeticException("No Found Programmer!!!"));

        var failureExceptionMessage = "No longer exist!!!!";

        pr.delete(found);

        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> pr.findById(this.id).orElseThrow(() -> new IllegalArgumentException(failureExceptionMessage)));

        Assertions.assertEquals(failureExceptionMessage, exception.getMessage());

    }
}
