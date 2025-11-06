package integrations;

import application.Main;
import configs.DataSourceConfig;
import entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import repositories.EmployeeRepositories;

//@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("test")
/*
@DataJpaTest(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        },
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = EmployeeRepositories.class), excludeAutoConfiguration = {DataSourceAutoConfiguration.class}
)*/
//@ContextConfiguration( classes = {Main.class})
//@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
//@ComponentScan(basePackages = {"repositories"})
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

/*@DataJpaTest(includeFilters =  @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = EmployeeRepositories.class))
@ContextConfiguration(classes={DataSourceConfig.class})
@EnableJpaRepositories(basePackages = {"repositories.*"})
@EntityScan("entities.*")*/
@SpringBootTest(classes = {DataSourceConfig.class, Main.class})
@EnableJpaRepositories(basePackages = {"repositories.*"})
@EntityScan("entities.*")
@ActiveProfiles("test")
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepositories employeeRepositories;

    /*public EmployeeRepositoryTests(EmployeeRepositories employeeRepositories) {
        this.employeeRepositories = employeeRepositories;
    }*/

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
