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

    @Autowired
    private ProjectRepository projectRepository;

    private Long id;

    @AfterAll
    public void cleanUp() {
        pr.deleteAll();
        Assertions.assertEquals(0, pr.count());
        projectRepository.deleteAll();
        Assertions.assertEquals(0, projectRepository.count());
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

        var find = pr.findById(this.id).orElseThrow();

        var failureMessage ="Cannot lazily initialize collection of role";

        Assertions.assertEquals(find.getId(), this.id);

        System.out.println(find);
        var exception = Assertions.assertThrows(LazyInitializationException.class, () -> find.getProjects().forEach(System.out::println));

        Matcher<String> matcher = CoreMatchers.containsString(failureMessage);

        MatcherAssert.assertThat(exception.getMessage(), matcher);


    }
}
