package com.jsharper.dyndns.server.repositories.inherintance;

import com.jsharper.dyndns.server.entities.inheritance.details.Address;
import com.jsharper.dyndns.server.entities.inheritance.people.Employee;
import com.jsharper.dyndns.server.entities.inheritance.people.Student;
import com.jsharper.dyndns.server.entities.inheritance.people.Teacher;
import com.jsharper.dyndns.server.repositories.inherintace.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository pr;


    @Test
    @Order(1)
    void createStudent_whenProvidedStudentInstance_returnStudentEntity() {
        var student = new Student(12, "test FirstName", "test lastName");

        var storedStudent = this.pr.save(student);

        Assertions.assertEquals(storedStudent, student);
    }


    @Test
    @Order(2)
    void createTeacher_whenProvidedTeacherInstance_returnTeacherEntity() {
        var teacher = new Teacher("Compiler Builder", "test FirstName", "test lastName");

        var storedTeacher = this.pr.save(teacher);

        Assertions.assertEquals(storedTeacher, teacher);
    }

    @Test
    @Order(3)
    void createEmployeeWithEmbeddedAddress_whenProvidedEmployeeInstance_returnEmployeeEntity() {

        var address = new Address("47 W 13St", "New York", "NY");

        var employee = new Employee("ENT-45852455-DT-DEV", "test FirstName", "test lastName", address);

        var storedEmployee = this.pr.save(employee);

        Assertions.assertEquals(storedEmployee, employee);
    }


}
