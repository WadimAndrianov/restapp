package ru.and.restapp.service.impl;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.and.restapp.dto.StudentDTO;
import ru.and.restapp.exceptions.MyExceptionBadRequest;
import ru.and.restapp.exceptions.MyExceptionNotFound;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class StudentServiceImplTest {

    @Mock
    private StudentsRepository studentsRepository; // @Mock используется для создания макета StudentsRepository,
                                                 // чтобы мы могли протестировать StudentService без реального studentsRepository
    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    AutoCloseable autoCloseable; //цель функции автоматического закрытия - закрыть все ненужные ресурсы, когда завергиться выполнение тестов

    //private StudentDTO studentDTO;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        studentService = new StudentServiceImpl(studentsRepository, groupRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testCreateStudent_Success() {
        // Установка поведения моков
        when(studentsRepository.findById(anyString())).thenReturn(Optional.empty());
        //Он говорит Mockito, что при вызове метода findById с любой строкой в качестве аргумента должен вернуться пустой Optional
        //
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new Group()));
        //при вызове метода findById с любой строкой в качестве аргумента должен вернуться Optional, содержащий новый объект Group
        // Вызов метода
        StudentDTO studentDTO = new StudentDTO("1", "John", "Doe", "john@example.com", 20, "group1");
        String result = studentService.createStudent(studentDTO);

        // Проверки
        assertEquals("Student has been successfully created", result);
        verify(studentsRepository).findById("1");//Эта строка проверяет, был ли вызван метод findById("1") на мок-объекте studentsRepository
        verify(groupRepository).findById("group1");
        verify(studentsRepository).save(any(Student.class)); //Эта строка проверяет, был ли вызван метод save на мок-объекте studentsRepository с любым объектом типа Student в качестве параметра
    }

    @Test
    public void testCreateStudent_ExistingStudent() {
        // Установка поведения моков
        when(studentsRepository.findById(anyString())).thenReturn(Optional.of(new Student()));

        StudentDTO studentDTO = new StudentDTO("1", "John", "Doe", "john@example.com", 20, "group1");

        // Проверка исключения
        assertThrows(MyExceptionBadRequest.class, () -> studentService.createStudent(studentDTO));
        verify(studentsRepository).findById("1");
    }

    @Test
    void testUpdateStudent_Successful() {
        // Arrange
        String studentId = "1";
        StudentDTO studentDTO = new StudentDTO(studentId, "John", "Doe", "john@example.com", 25, "group1");

        Student existingStudent = new Student("John", "Doe", "john@example.com", studentId, 24, null);

        when(studentsRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        when(groupRepository.findById("group1")).thenReturn(Optional.of(new Group("group1", "CN", null)));

        // Act
        String result = studentService.updateStudent(studentDTO);

        // Assert
        assertEquals("Student updated successful", result);
    }

    @Test
    void testUpdateStudent_StudentNotFound() {
        // Arrange
        String studentId = "1";
        StudentDTO studentDTO = new StudentDTO(studentId, "John", "Doe", "john@example.com", 25, "group1");

        when(studentsRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MyExceptionBadRequest.class, () -> studentService.updateStudent(studentDTO));
    }

    @Test
    void testUpdateStudent_GroupNotFound() {
        String studentId = "1";
        String groupId = "group1";
        StudentDTO studentDTO = new StudentDTO(studentId, "John", "Doe", "john@example.com", 25, groupId);

        Student existingStudent = new Student("John", "Doe", "john@example.com", studentId, 24, null);

        when(studentsRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        assertThrows(MyExceptionBadRequest.class, () -> studentService.updateStudent(studentDTO));
    }

    @Test
    void testDeleteStudent_Success() {
        // Arrange
        String studentId = "1";
        Student existingStudent = new Student("John", "Doe", "john@example.com", studentId, 24, null);
        when(studentsRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        // Act
        String result = studentService.deleteStudent(studentId);

        // Assert
        assertEquals("Student has been successfully deleted", result);
        verify(studentsRepository, times(1)).deleteById(studentId);
        //Эта строка кода проверяет, был ли вызван метод deleteById у объекта studentsRepository ровно один раз (times(1))
        // с указанным аргументом studentId. Если метод вызывается больше или меньше одного раза, то тест не будет пройден.
    }

    @Test
    void testDeleteStudent_NotSuccess() {
        // Arrange
        String studentId = "1";
        when(studentsRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MyExceptionNotFound.class, () -> studentService.deleteStudent(studentId));
        verify(studentsRepository, never()).deleteById(studentId);
        //Эта строка кода проверяет, что метод deleteById у объекта studentsRepository никогда не был вызван с указанным аргументом studentId. Если метод
        //был вызван хотя бы один раз с этим аргументом, то тест не будет пройден.
    }
    @Test
    void testGetStudent() {
        // Задаем ожидаемое значение
        String studentId = "1";
        Student expectedStudent = new Student();
        when(studentsRepository.findById(studentId)).thenReturn(Optional.of(expectedStudent));

        // Вызываем метод сервиса
        Optional<Student> actualStudentOptional = studentService.getStudent(studentId);

        verify(studentsRepository).findById(studentId);

        // Проверяем, что возвращается ожидаемый студент
        assertEquals(expectedStudent, actualStudentOptional.orElse(null));
    }
    @Test
    void testCreateStudents() {
        // Создаем список объектов StudentDTO для теста
        List<StudentDTO> studentDTOList = new ArrayList<>();
        studentDTOList.add(new StudentDTO("1", "John", "Doe", "john@example.com", 25, "group1"));
        studentDTOList.add(new StudentDTO("2", "Bob", "Doe", "bob@example.com", 23, "group1"));

        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new Group("group1", "NK", null)));
        when(studentsRepository.findById(anyString())).thenReturn(Optional.empty());
        // Вызываем метод, который мы тестируем
        studentService.createStudents(studentDTOList);

        // Проверяем, что метод createStudent вызывался для каждого объекта в списке
        verify(studentsRepository, times(studentDTOList.size())).save(any(Student.class));
    }
}