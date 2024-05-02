package ru.and.restapp.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.and.restapp.dto.StudentDTO;
import ru.and.restapp.model.Group;
import ru.and.restapp.repository.GroupRepository;
import ru.and.restapp.repository.StudentsRepository;
import ru.and.restapp.service.StudentService;

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

    private StudentDTO studentDTO;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        studentService = new StudentServiceImpl(studentsRepository, groupRepository);
        studentDTO = new StudentDTO("01", "Wadim", "Andrianov", "slaveofGod23@outlook.com", 19, "250501");
    }
    //  public StudentDTO(String studentId, String firstName, String lastName, String email, int age, String groupId)
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testGetStudents() {
    }

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
}