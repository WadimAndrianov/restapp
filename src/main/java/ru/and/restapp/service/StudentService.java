package ru.and.restapp.service;


import org.springframework.http.ResponseEntity;
import ru.and.restapp.model.Student;
import ru.and.restapp.model.StudentDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public String createStudent(Student student);

    public String updateStudent(Student student);

    public String deleteStudent(String studentId);

    public Optional<Student> getStudent(String studentId);

    public List<Student> getAllStudent();
}
