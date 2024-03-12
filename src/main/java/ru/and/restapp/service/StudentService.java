package ru.and.restapp.service;


import org.springframework.http.ResponseEntity;
import ru.and.restapp.model.Student;

import java.util.List;

public interface StudentService {
    public String createStudent(Student student);
    public String updateStudent(Student student);
    public String deleteStudent(String studentId);
    public ResponseEntity<Student> getStudent(String studentId);
    public List<Student> getAllStudent();
}
