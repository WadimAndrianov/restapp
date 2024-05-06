package ru.and.restapp.service.impl;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.and.restapp.dto.StudentDTO;
import ru.and.restapp.model.Group;
import ru.and.restapp.model.Student;
import ru.and.restapp.repository.GroupRepository;
import ru.and.restapp.repository.StudentsRepository;
import ru.and.restapp.service.StudentService;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class StudentServiceImplTest {

    @Mock
    private StudentsRepository studentsRepository; // @Mock используется для создания макета StudentsRepository,
                                                 // чтобы мы могли протестировать StudentService без реального studentsRepository
    @Mock
    private GroupRepository groupRepository;

    private StudentService studentService;

    AutoCloseable autoCloseable; //цель функции автоматического закрытия - закрыть все ненужные ресурсы, когда завергиться выполнение тестов

    //private StudentDTO studentDTO;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        studentService = new StudentServiceImpl(studentsRepository, groupRepository);
        //studentDTO = new StudentDTO("01", "Wadim", "Andrianov", "slaveofGod23@outlook.com", 19, "250501");
    }
    //  public StudentDTO(String studentId, String firstName, String lastName, String email, int age, String groupId)
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testGetStudents() {
        // Создаем тестовые данные
        //Group(String groupId, String curatorName, List<Student> studentList)
        //Student(String firstName, String lastName, String email, String studentId, int age, Group group)
        Group group1 = new Group("250501", "Anas Mich", null);
        Student student1 = new Student("1", "John", "Doe", "john@example.com", 25, group1);
        Student student2 = new Student("2", "Jane", "Smith", "jane@example.com", 22, group1);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        // Создаем ожидаемый список DTO
        List<StudentDTO> expectedDTOList = new ArrayList<>();
        expectedDTOList.add(new StudentDTO("1", "John", "Doe", "john@example.com", 25, "250501"));
        expectedDTOList.add(new StudentDTO("2", "Jane", "Smith", "jane@example.com", 22, "250501"));

        // Mock поведения репозитория
        when(studentsRepository.findByParam(null, null)).thenReturn(studentList);

        // Вызываем тестируемый метод
        List<StudentDTO> actualDTOList = studentService.getStudents(null, null);

        // Проверяем результат
        assertThat(actualDTOList).hasSize(2);
        assertThat(actualDTOList).containsExactlyInAnyOrderElementsOf(expectedDTOList);

        //Assertions.assertThat()
    }

    }
/*
    @Test
    void testCreateStudent() {
        mock(StudentDTO.class);
        mock(StudentsRepository.class);//Эта строка создает мок объекта класса StudentDTO.
        // Моки - это объекты, которые имитируют поведение реальных объектов, но не имеют реальной реализации и возвращают
        // "пустые" значения по умолчанию для методов и полей
        String result = studentService.createStudent(studentDTO);
        // Проверяем результат
        assertThat(result).isEqualTo("Student has been successfully created");


    }

    @Test
    void testCreateStudents() {
    }

    @Test
    void testUpdateStudent() {
    }

    @Test
    void testDeleteStudent() {
    }

    @Test
    void testGetStudent() {
    }
    */

//}