package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.Student;
import com.jsharper.dyndns.server.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository sr;

    @Autowired
    private ResourceLoader resourceLoader;

    private List<Student> inputStudents;


    @BeforeAll
    public void init() throws IOException {

        inputStudents = this.getResource();

        Assertions.assertNotNull(inputStudents);

        Supplier<Stream<Student>> products = () -> StreamSupport
                .stream(sr.saveAll(inputStudents).spliterator(), false);

        Assertions.assertTrue(products.get().findAny().isPresent());
    }

    @AfterAll
    public void cleanUp() {
        sr.deleteAll();

        Assertions.assertEquals(0, sr.count());
    }

    @Test
    @Order(1)
    @DisplayName("create student when provided student instance return store student entity")
    void createStudent_whenProvidedStudentInstance_returnStoredStudentEntity() {
        var student = new Student("Test FirstName", "Test LastName", 23.2);

        var storedStudent = sr.save(student);

        Assertions.assertEquals(student.getFirstName(), storedStudent.getFirstName());
        Assertions.assertEquals(student.getLastName(), storedStudent.getLastName());
        Assertions.assertNotNull(storedStudent.getId());
    }

    @TestFactory
    @Order(2)
    @DisplayName("create student when provided student instance return store student entity")
    Stream<DynamicTest> findAllByQueryJPQLAndCompareToStoreEntity_whenProvidedStartingSaveAllListOfStudent_returnStoredStudentEntities() {

        var pairs = StreamUtils.zip(sr.findAllStudents().stream(), this.inputStudents.stream(), Pair::of);

        return pairs.map(p -> DynamicTest.dynamicTest(
                String.format(
                        "First from JPQL id=%d firstName=%s lastName=%s score%f\\n Second from JPQL id=%d firstName=%s lastName=%s score%f",
                        p.getFirst().getId(), p.getFirst().getFirstName(), p.getFirst().getLastName(), p.getFirst().getScore(),
                        p.getSecond().getId(), p.getSecond().getFirstName(), p.getSecond().getLastName(), p.getSecond().getScore()


                ),
                () -> {
                    Assertions.assertEquals(p.getFirst().getId(), p.getSecond().getId());
                    Assertions.assertEquals(p.getFirst().getFirstName(), p.getSecond().getFirstName());
                    Assertions.assertEquals(p.getFirst().getLastName(), p.getSecond().getLastName());
                    Assertions.assertEquals(p.getFirst().getScore(), p.getSecond().getScore());

                }

        ));


    }

    @TestFactory
    @Order(3)
    @DisplayName("find all students of partially properties")
    Stream<DynamicTest> findPartialAllByQueryJPQLAndCompareToStoreEntity_whenProvidedStartingSaveAllListOfStudent_returnStoredStudentEntities() {

        var castedStudent = sr.findStudentsPartialData().stream().map(props -> new Student((String) props[0], (String) props[1], 0.0)).toList();

        var pairs = StreamUtils.zip(castedStudent.stream(), this.inputStudents.stream(), Pair::of);

        return pairs.map(p -> DynamicTest.dynamicTest(
                String.format(
                        "First from JPQL id=%d firstName=%s lastName=%s score%f\\n Second from JPQL id=%d firstName=%s lastName=%s score%f",
                        p.getFirst().getId(), p.getFirst().getFirstName(), p.getFirst().getLastName(), p.getFirst().getScore(),
                        p.getSecond().getId(), p.getSecond().getFirstName(), p.getSecond().getLastName(), p.getSecond().getScore()


                ),
                () -> {
                    Assertions.assertEquals(p.getFirst().getFirstName(), p.getSecond().getFirstName());
                    Assertions.assertEquals(p.getFirst().getLastName(), p.getSecond().getLastName());

                }

        ));


    }


    @TestFactory
    @Order(4)
    @DisplayName("find all by query JPQL by firstname")
    Stream<DynamicTest> findAllByQueryJPQLPartiallyPropertiesAndCompareToStoreEntity_whenProvidedStartingSaveAllListOfStudent_returnStoredStudentEntities() {

        var firstName = "Jonathan";

        var foundList = inputStudents.stream().filter(s -> s.getFirstName().equals(firstName)).toList();

        var pairs = StreamUtils.zip(sr.findAllStudentsByFirstName(firstName).stream(), foundList.stream(), Pair::of);

        return pairs.map(p -> DynamicTest.dynamicTest(
                String.format(
                        "First from JPQL id=%d firstName=%s lastName=%s score%f\\n Second from JPQL id=%d firstName=%s lastName=%s score%f",
                        p.getFirst().getId(), p.getFirst().getFirstName(), p.getFirst().getLastName(), p.getFirst().getScore(),
                        p.getSecond().getId(), p.getSecond().getFirstName(), p.getSecond().getLastName(), p.getSecond().getScore()


                ),
                () -> {
                    Assertions.assertEquals(p.getFirst().getId(), p.getSecond().getId());
                    Assertions.assertEquals(p.getFirst().getFirstName(), p.getSecond().getFirstName());
                    Assertions.assertEquals(p.getFirst().getLastName(), p.getSecond().getLastName());
                    Assertions.assertEquals(p.getFirst().getScore(), p.getSecond().getScore());

                }

        ));


    }

    @TestFactory
    @Order(5)
    @DisplayName("find all by query JPQL by score min and max")
    Stream<DynamicTest> findAllByQueryJPQLPartiallyPropertyScoreMinAndMaxAndCompareToStoreEntity_whenProvidedStartingSaveAllListOfStudent_returnStoredStudentEntities() {

        var min = 70.0;

        var max = 80.0;

        var foundList = inputStudents.stream().filter(s -> s.getScore() > min && s.getScore() < max).toList();

        var pairs = StreamUtils.zip(sr.findAllForGivenScores(min, max).stream(), foundList.stream(), Pair::of);

        return pairs.map(p -> DynamicTest.dynamicTest(
                String.format(
                        "First from JPQL id=%d firstName=%s lastName=%s score%f\\n Second from JPQL id=%d firstName=%s lastName=%s score%f",
                        p.getFirst().getId(), p.getFirst().getFirstName(), p.getFirst().getLastName(), p.getFirst().getScore(),
                        p.getSecond().getId(), p.getSecond().getFirstName(), p.getSecond().getLastName(), p.getSecond().getScore()


                ),
                () -> {
                    Assertions.assertEquals(p.getFirst().getId(), p.getSecond().getId());
                    Assertions.assertEquals(p.getFirst().getFirstName(), p.getSecond().getFirstName());
                    Assertions.assertEquals(p.getFirst().getLastName(), p.getSecond().getLastName());
                    Assertions.assertEquals(p.getFirst().getScore(), p.getSecond().getScore());
                    Assertions.assertTrue(p.getFirst().getScore() > min && p.getFirst().getScore() < max);
                    Assertions.assertTrue(p.getSecond().getScore() > min && p.getSecond().getScore() < max);

                }

        ));


    }

    @Test
    @DisplayName("delete student by firstName")
    @Order(6)
    @Transactional
    void deleteStudentByFirstName_whenProvidedFirstName_returnVoid() {
        var firstName = "Jonathan";

        this.sr.deleteStudentsByFirstName(firstName);

        var list = this.sr.findAllStudentsByFirstName(firstName);

        Assertions.assertEquals(0, list.size());

    }


    private List<Student> getResource() throws IOException {
        var sources = resourceLoader.getResource("classpath:/students.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(sources.getFile().toURI()), new TypeReference<List<Student>>() {
        });
    }
}
