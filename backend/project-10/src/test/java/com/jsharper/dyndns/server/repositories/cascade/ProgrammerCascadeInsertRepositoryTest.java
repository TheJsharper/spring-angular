package com.jsharper.dyndns.server.repositories.cascade;

import com.jsharper.dyndns.server.entities.cascade.ProgrammerCascadeInsert;
import com.jsharper.dyndns.server.entities.cascade.ProjectCascadeInsert;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hibernate.engine.spi.SessionImplementor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProgrammerCascadeInsertRepositoryTest {

    @Autowired
    private ProgrammerCascadeInsertRepository repository;


    @PersistenceUnit
    private EntityManagerFactory emf;

    private EntityManager entityManager;

    @BeforeAll
    public void setUp() {
        entityManager = emf.createEntityManager();
    }

    @AfterAll
    public void shutdown() {
        System.out.println("AfterTestClass");
        entityManager.close();
    }

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
        Assertions.assertEquals(0, repository.count());
    }

    @Test
    @Order(1)
    void createAndSavePropagatedFromParentToChildren_providedParentObjectAndListOfChildren_returnStoredEntity() {

        var projects = new HashSet<ProjectCascadeInsert>();

        projects.add(new ProjectCascadeInsert("SpringFramework Project"));

        var programmer = new ProgrammerCascadeInsert("firstName", "lastName", projects);

        var storedProgrammer = repository.save(programmer);

        Assertions.assertEquals(storedProgrammer, programmer);
        Assertions.assertEquals(1, storedProgrammer.getProjectCascadeInserts().size());
    }

    @Test
    @Order(2)
    void createAndPersistWithEntityManagerPropagatedFromParentToChildren_providedParentObjectAndListOfChildren_returnStoredEntity() {

        SessionImplementor session = (SessionImplementor) entityManager.getDelegate();

        var transaction = session.getTransaction();

        transaction.begin();

        var projects = new HashSet<ProjectCascadeInsert>();

        projects.add(new ProjectCascadeInsert("SpringFramework Project"));

        var programmer = new ProgrammerCascadeInsert("firstName", "lastName", projects);

        session.persist(programmer);


        var storedProgrammer = entityManager.find(ProgrammerCascadeInsert.class, programmer.getId());

        session.flush();
        session.clear();
        transaction.commit();

        Assertions.assertEquals(storedProgrammer, programmer);
        Assertions.assertEquals(1, storedProgrammer.getProjectCascadeInserts().size());

    }


    @Test
    @Order(3)
    void createAndTryPersistWithEntityManagerPropagatedFromParentToChildren_providedParentObjectAndListOfChildren_returnException() {

        SessionImplementor session = (SessionImplementor) entityManager.getDelegate();

        var transaction = session.getTransaction();

        transaction.begin();

        var failureMessage = "Detached entity passed to persist:";

        var projects = new HashSet<ProjectCascadeInsert>();

        projects.add(new ProjectCascadeInsert("SpringFramework Project"));

        var programmer = new ProgrammerCascadeInsert("firstName", "lastName", projects);

        session.persist(programmer);


        var storedProgrammer = entityManager.find(ProgrammerCascadeInsert.class, programmer.getId());

        session.detach(projects.toArray()[0]);

        var exception = Assertions.assertThrows(EntityExistsException.class, () -> {
            session.persist(programmer);
            session.flush();
            session.clear();
            transaction.rollback();
        });

        Matcher<String> matcher = CoreMatchers.containsString(failureMessage);

        MatcherAssert.assertThat(exception.getMessage(), matcher);

        Assertions.assertEquals(storedProgrammer, programmer);

        Assertions.assertEquals(1, storedProgrammer.getProjectCascadeInserts().size());

    }
}
