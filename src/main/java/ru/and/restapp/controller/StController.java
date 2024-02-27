package ru.and.restapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.and.restapp.model.Student;
import ru.and.restapp.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StController {
    StudentService studentService;
    public StController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{studentId}")
    public ResponseEntity<?> getStudent(@PathVariable("studentId") String studentId) {
        return studentService.getStudent(studentId);
    }
    @GetMapping()
    public List<Student> getAllStudent() {
        return studentService.getAllStudent();
    }
    @PostMapping
    public String createStudent(@RequestBody Student student){
        studentService.createStudent(student);
        return "The student has been successfully created";
    }
    @PutMapping
    public String updateStudent(@RequestBody Student student){
        studentService.updateStudent(student);
        return "The student has been successfully updated";
    }
    @DeleteMapping("{studentId}")
    public String deleteStudent(@PathVariable("studentId") String studentId){
        studentService.deleteStudent(studentId);
        return "The student has been successfully deleted";
    }

}