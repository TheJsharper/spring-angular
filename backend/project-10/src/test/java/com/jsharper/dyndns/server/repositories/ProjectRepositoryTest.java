package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.fetch.ProjectFetch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository pr;

    @Test
    void test() {
        var project = new ProjectFetch("Test Name");

        var storeProject = pr.save(project);

        Assertions.assertEquals(project, storeProject);
    }
}
