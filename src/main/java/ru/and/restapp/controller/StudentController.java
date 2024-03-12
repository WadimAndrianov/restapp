package ru.and.restapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.and.restapp.model.Student;
import ru.and.restapp.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable("studentId") String studentId) {
        return studentService.getStudent(studentId);
    }
    @GetMapping()
    public List<Student> getAllStudent() {
        return studentService.getAllStudent();
    }
    @PostMapping
    public String createStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }
    @PutMapping
    public String updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }
    @DeleteMapping("{studentId}")
    public String deleteStudent(@PathVariable("studentId") String studentId){
        return studentService.deleteStudent(studentId);
    }

}