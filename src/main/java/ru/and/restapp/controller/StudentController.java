package ru.and.restapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.and.restapp.cache.CacheManager;
import ru.and.restapp.exceptions.MyExceptionNotFound;
import ru.and.restapp.model.Student;
import ru.and.restapp.dto.StudentDTO;
import ru.and.restapp.service.GroupService;
import ru.and.restapp.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private CacheManager cache;
    private StudentService studentService;
    private GroupService groupService;

    public StudentController(final StudentService studentService, GroupService groupService, CacheManager cache) {
        this.studentService = studentService;
        this.groupService = groupService;
        this.cache = cache;
    }

    @GetMapping()
    public List<StudentDTO> getAllStudent(@RequestParam(name = "age", required = false) Integer age,
                                          @RequestParam(name = "email", required = false) String email) {
        return studentService.getStudents(age, email);
    }

    @GetMapping("{studentId}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable("studentId") String studentId) {
        Optional<StudentDTO> optionalStudentDTO = cache.getStudentDTOfromCache(studentId);
        if (optionalStudentDTO.isEmpty()) {
            Optional<Student> optionalStudent = studentService.getStudent(studentId);
            if (optionalStudent.isEmpty()) {
                //return ResponseEntity.notFound().build();
                throw new MyExceptionNotFound("A student with this Id was not found");
            } else {
                Student student = optionalStudent.get();
                if (student.getGroup() != null) {
                    StudentDTO studentDTO = new StudentDTO(studentId, student.getFirstName(), student.getLastName(),
                            student.getEmail(), student.getAge(), student.getGroup().getGroupId());
                    cache.addStudentDTOtoCache(studentId, studentDTO);
                    return ResponseEntity.ok(studentDTO);
                } else {
                    StudentDTO studentDTO = new StudentDTO(studentId, student.getFirstName(), student.getLastName(),
                            student.getEmail(), student.getAge(), null);
                    cache.addStudentDTOtoCache(studentId, studentDTO);
                    return ResponseEntity.ok(studentDTO);
                }

            }
        } else {
            return ResponseEntity.ok(optionalStudentDTO.get());
        }
    }

    @GetMapping("/cache")
    public List<StudentDTO> getStudentOnlyFromCache() {
        return cache.getAllStudentCache();
    }

    @PostMapping
    public String createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @PutMapping
    public String updateStudent(@Valid @RequestBody StudentDTO studentDTO) {
        cache.removeStudentDTOfromCache(studentDTO.getStudentId()); //удаляем если есть в кэше
        return studentService.updateStudent(studentDTO);
    }

    @DeleteMapping("{studentId}")
    public String deleteStudent(@PathVariable("studentId") String studentId) {
        cache.removeStudentDTOfromCache(studentId); //удаляем если есть в кэше
        return studentService.deleteStudent(studentId);
    }

}