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

    @GetMapping()
    public List<StudentDTO> getAllStudent(@RequestParam(name = "age", required = false) Integer age,
    @RequestParam(name = "email", required = false) String email) {
        if (age == null && email == null) {
            return studentService.getStudentByAgeAndEmail(null, null);
        } else if (age != null && email == null) {
            return studentService.getStudentByAgeAndEmail(age, null);
        }else if(age == null && email != null){
            return studentService.getStudentByAgeAndEmail(null, email);
        }else{
            return studentService.getStudentByAgeAndEmail(age, email);
        }

/*
        if (age != null) {
            return studentService.getStudentsByAge(age);
        } else {
            return studentService.getAllStudent();
        }*/
    }

    @GetMapping("{studentId}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable("studentId") String studentId) {
        Optional<Student> optionalStudent = studentService.getStudent(studentId);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Student student = optionalStudent.get();
            //String studentId, String firstName, String lastName, String email, int age, String groupId
            if (student.getGroup() != null) {
                StudentDTO studentDTO = new StudentDTO(studentId, student.getFirstName(), student.getLastName(),
                        student.getEmail(), student.getAge(), student.getGroup().getGroupId());
                return ResponseEntity.ok(studentDTO);
            } else {
                StudentDTO studentDTO = new StudentDTO(studentId, student.getFirstName(), student.getLastName(),
                        student.getEmail(), student.getAge(), null);
                return ResponseEntity.ok(studentDTO);
            }

        }
    }


    @PostMapping
    public String createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @PutMapping
    public String updateStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.updateStudent(studentDTO);
    }

    @DeleteMapping("{studentId}")
    public String deleteStudent(@PathVariable("studentId") String studentId) {
        return studentService.deleteStudent(studentId);
    }

}