package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.Programmer;
import com.jsharper.dyndns.server.entities.Project;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProgrammerRepositoryTest {

    @Autowired
    private ProgrammerRepository pr;

    @Autowired
    private ProjectRepository projectRepository;

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
        var programmer = new Programmer("Test FirstName", "Test LastName", 2500);

        var storedProgrammer = pr.save(programmer);

        Assertions.assertEquals(programmer, storedProgrammer);
    }

    @Test
    @Order(2)
    void createProgrammerInstanceWithRelatedProjectInstance_ProvidedProgrammerInstanceWithRelatedProject_returnStoredEntityWithItsRelation() {
        var projects = new HashSet<Project>();

        var project = new Project("Spring Framework");

        projects.add(project);

        var programmer = new Programmer("Test FirstName", "Test LastName", 2500, projects);

        var storedProgrammer = pr.save(programmer);

        Assertions.assertEquals(programmer, storedProgrammer);
        Assertions.assertEquals(1, storedProgrammer.getProjects().size());
    }
}
