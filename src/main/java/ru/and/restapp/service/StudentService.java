package ru.and.restapp.service;


import org.springframework.http.ResponseEntity;
import ru.and.restapp.model.Student;
import ru.and.restapp.model.StudentDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public String createStudent(StudentDTO studentDTO);

    public String updateStudent(StudentDTO studentDTO);

    public String deleteStudent(String studentId);

    public Optional<Student> getStudent(String studentId);

    public List<StudentDTO> getStudentsByAge(int age);
    public List<StudentDTO> getStudentsByEmail(String email);


    public List<StudentDTO> getStudentByAgeAndEmail(Integer age, String email);


    public List<StudentDTO> getAllStudent();
}
