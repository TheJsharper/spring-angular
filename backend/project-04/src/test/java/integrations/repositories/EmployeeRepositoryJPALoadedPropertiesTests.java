package integrations.repositories;

import application.Main;
import configs.DataSourceConfig;
import entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import repositories.EmployeeRepositories;


@DataJpaTest(
        excludeAutoConfiguration = {DataSourceAutoConfiguration.class}
)
@ContextConfiguration(classes = {Main.class, DataSourceConfig.class})
@ActiveProfiles("test")
public class EmployeeRepositoryJPALoadedPropertiesTests {

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

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee("Test firstName", "Test lastName");

        var saveEmployee = employeeRepositories.save(employee);

        var foundEmployee = employeeRepositories.findById(employee.getId()).orElseThrow();

        foundEmployee.setFirstName("!Test update firstName");

        foundEmployee.setLastName("!Test update lasName");

        employeeRepositories.save(foundEmployee);

        Assertions.assertEquals(foundEmployee.getId(), saveEmployee.getId());

        Assertions.assertEquals(foundEmployee.getFirstName(), saveEmployee.getFirstName());

        Assertions.assertEquals(foundEmployee.getLastName(), saveEmployee.getLastName());

        Assertions.assertEquals(saveEmployee, foundEmployee);


        foundEmployee.setFirstName("!Test update firstName");
        foundEmployee.setLastName("!Test update lasName");

       var updateEmployee = employeeRepositories.save(foundEmployee);


        Assertions.assertEquals(foundEmployee.getId(), updateEmployee.getId());

        Assertions.assertEquals(foundEmployee.getFirstName(), updateEmployee.getFirstName());

        Assertions.assertEquals(foundEmployee.getLastName(), updateEmployee.getLastName());

        Assertions.assertEquals(foundEmployee, updateEmployee);

    }
}
