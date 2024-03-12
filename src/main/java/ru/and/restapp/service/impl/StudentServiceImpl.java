package ru.and.restapp.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.and.restapp.model.Student;
import ru.and.restapp.repository.StudentsRepository;
import ru.and.restapp.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    StudentsRepository studentsRepository;

    public StudentServiceImpl(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    @Override
    public String createStudent(Student student) {
        Optional<Student> StudentCont = studentsRepository.findById(student.getStudentId());
        if(StudentCont.isEmpty()){
            studentsRepository.save(student);
            return "The student has been successfully created";
        }else{
            return "Failed operation. Student with id " + student.getStudentId() + " already exists";
        }

    }

    @Override
    public String updateStudent(Student student) {
        Optional<Student> optionalCont = studentsRepository.findById(student.getStudentId());
        if(optionalCont.isEmpty()){
            return "This student is not in the database";
        }else{
            studentsRepository.save(student);
            return "Student updated successful";
        }

    }

    @Override
    public String deleteStudent(String studentId) {
        Optional<Student> optionalCont = studentsRepository.findById(studentId);
        if(optionalCont.isEmpty()){
            return "This student is not in the database";
        }else{
            studentsRepository.deleteById(studentId);
            return "Student has been successfully deleted";
        }
    }

    @Override
    public List<Student> getAllStudent() {
        return studentsRepository.findAll();
    }

    @Override
    public ResponseEntity<Student> getStudent(String studentId) {
        Optional<Student> optionalCont = studentsRepository.findById(studentId);
        if (optionalCont.isEmpty())
            return  ResponseEntity.notFound().build(); // Возвращаем HTTP статус 404 Not Found
        else {
            Student student = optionalCont.get();
            return ResponseEntity.ok(student);
        }
    }
}
