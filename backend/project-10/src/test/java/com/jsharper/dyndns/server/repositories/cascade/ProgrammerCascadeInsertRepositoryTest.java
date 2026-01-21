package com.jsharper.dyndns.server.repositories.cascade;

import com.jsharper.dyndns.server.entities.cascade.ProgrammerCascadeInsert;
import com.jsharper.dyndns.server.entities.cascade.ProjectCascadeInsert;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
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
    private Long id;

    @AfterAll
    public void cleanUp() {
        repository.deleteAll();
        Assertions.assertEquals(0, repository.count());
    }

    @Test
    void test() {

        var projects = new HashSet<ProjectCascadeInsert>();
        projects.add(new ProjectCascadeInsert("SpringFramework Project"));
        var programmer = new ProgrammerCascadeInsert("firstName", "lastName", projects);

        var storedProgrammer = repository.save(programmer);
        this.id = storedProgrammer.getId();
        Assertions.assertEquals(storedProgrammer, programmer);
        Assertions.assertEquals(1, storedProgrammer.getProjectCascadeInserts().size());
    }

    @Test
    void test2() {
        //entityManager.getTransaction().begin();
        var entityManager = emf.createEntityManager();
        SessionImplementor session = (SessionImplementor) entityManager.getDelegate();
        var transaction = entityManager.getTransaction();
        transaction.begin();
        var projects = new HashSet<ProjectCascadeInsert>();
        projects.add(new ProjectCascadeInsert("SpringFramework Project"));
        var programmer = new ProgrammerCascadeInsert("firstName", "lastName", projects);

        entityManager.persist(programmer);


        var storedProgrammer = entityManager.find(ProgrammerCascadeInsert.class, programmer.getId());
        transaction.commit();
        this.id = storedProgrammer.getId();
        Assertions.assertEquals(storedProgrammer, programmer);
        Assertions.assertEquals(1, storedProgrammer.getProjectCascadeInserts().size());

    }
}
