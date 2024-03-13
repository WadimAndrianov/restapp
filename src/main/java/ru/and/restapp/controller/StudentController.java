package ru.and.restapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.and.restapp.model.Student;
import ru.and.restapp.model.StudentDTO;
import ru.and.restapp.service.GroupService;
import ru.and.restapp.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    StudentService studentService;
    GroupService groupService;
    public StudentController(StudentService studentService, GroupService groupService) {

        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable("studentId") String studentId) {
        Optional<Student> optionalStudent = studentService.getStudent(studentId);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(optionalStudent.get());
        }
    }

    @GetMapping()
    public List<Student> getAllStudent() {
        return studentService.getAllStudent();
    }

    @PostMapping
    public String createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public String updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("{studentId}")
    public String deleteStudent(@PathVariable("studentId") String studentId) {
        return studentService.deleteStudent(studentId);
    }

}