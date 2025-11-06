package integrations.repositories;

import application.Main;
import configs.DataSourceConfig;
import entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import repositories.EmployeeRepositories;


@SpringBootTest(classes = {DataSourceConfig.class, Main.class})
@EnableJpaRepositories(basePackages = {"repositories.*"})
@EntityScan("entities.*")
@ActiveProfiles("test")
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepositories employeeRepositories;


    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee("Test firstName", "Test lastName");

        var foundEmployee = employeeRepositories.save(employee);

        Assertions.assertEquals(foundEmployee.getId(), employee.getId());

        Assertions.assertEquals(foundEmployee.getFirstName(), employee.getFirstName());

        Assertions.assertEquals(foundEmployee.getLastName(), employee.getLastName());

        Assertions.assertEquals(foundEmployee, employee);

    }
}
