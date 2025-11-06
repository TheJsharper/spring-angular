package integrations;

import application.Main;
import configs.DataSourceConfig;
import entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import repositories.EmployeeRepositories;


@DataJpaTest(
        excludeAutoConfiguration = {DataSourceAutoConfiguration.class}
)
@ContextConfiguration( classes = {Main.class, DataSourceConfig.class})
@ActiveProfiles("test")
public class EmployeeRepositoryJPALoadedPropertiesTests {

    @Autowired
    private EmployeeRepositories employeeRepositories;


    @Test
    public void testCreateEmployee() {
        Employee employee =  new Employee("Test firstName", "Test lastName");

        var foundEmployee =  employeeRepositories.save(employee);

        Assertions.assertEquals(foundEmployee.getId(), employee.getId());

        Assertions.assertEquals(foundEmployee.getFirstName(), employee.getFirstName());

        Assertions.assertEquals(foundEmployee.getLastName(), employee.getLastName());

        Assertions.assertEquals(foundEmployee, employee);

    }
}
