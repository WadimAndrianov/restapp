package ru.and.restapp.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.and.restapp.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class StudentsRepositoryTest {
    @Autowired
    private StudentsRepository studentsRepository;

    Student student;

    @BeforeEach
    void setUp() {
        student = new Student("Ivan", "Ivanov", "ivanov@yandex.ru", "01", 18, null);

        Student student1 = new Student("Egor", "Egorov", "egor@yandex.ru", "02",20, null);
        Student student2 = new Student("Nastya", "Nastyevna", "nastya@gmail.com", "03",18, null);
        Student student3 = new Student("Ilia", "Makarov", "mak@outlook.com", "04",20, null);

        Student student4 = new Student("Anton", "Antonov", "antonov@yandex.ru", "05",18, null);

        studentsRepository.save(student);
        studentsRepository.save(student1);
        studentsRepository.save(student2);
        studentsRepository.save(student3);
        studentsRepository.save(student4);
    }

    @AfterEach
    void tearDown() {
        student = null;
        studentsRepository.deleteAll();
    }
    @Test
    public void testFindByParamFound() {
        List<Student> students = studentsRepository.findByParam(18, "yandex.ru");
        assertEquals(2, students.size());
        students.clear();

        students = studentsRepository.findByParam(18, null);
        assertEquals(3, students.size());
        students.clear();

        students = studentsRepository.findByParam(null, "outlook.com");
        assertEquals(1, students.size());
    }
    @Test
    void testFindByParamNotFound()
    {
        List<Student> students = studentsRepository.findByParam(20, "gmail.com");
        assertTrue(students.isEmpty());
    }
}
