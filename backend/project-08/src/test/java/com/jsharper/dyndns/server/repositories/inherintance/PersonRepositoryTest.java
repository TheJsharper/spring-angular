package com.jsharper.dyndns.server.repositories.inherintance;

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
    void createStudent_whenProvidedStudentInstance_returnStudentEntity(){
        var student = new Student(12,"test FirstName", "test lastName");

        var storedStudent= this.pr.save(student);

        System.out.println(student);
        System.out.println(storedStudent);

        Assertions.assertEquals(storedStudent, student);
    }


    @Test
    @Order(2)
    void createTeacher_whenProvidedTeacherInstance_returnTeacherEntity(){
        var teacher = new Teacher("Compiler Builder","test FirstName", "test lastName");

        var storedTeacher= this.pr.save(teacher);

        System.out.println(teacher);
        System.out.println(storedTeacher);

        Assertions.assertEquals(storedTeacher, teacher);
    }


}
