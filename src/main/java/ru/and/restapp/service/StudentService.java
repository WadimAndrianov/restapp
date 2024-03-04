package ru.and.restapp.service;


import org.springframework.http.ResponseEntity;
import ru.and.restapp.model.Student;

import java.util.List;

public interface StudentService {
    public void createStudent(Student student);
    public void updateStudent(Student student);
    public void deleteStudent(String studentId);
    public ResponseEntity<Student> getStudent(String studentId);
    public List<Student> getAllStudent();
}
