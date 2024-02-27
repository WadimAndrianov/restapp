package ru.and.restapp.service.impl;

import org.springframework.http.HttpStatus;
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
    public void createStudent(Student student) {
        studentsRepository.save(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentsRepository.save(student);
    }

    @Override
    public void deleteStudent(String studentId) {
        studentsRepository.deleteById(studentId);
    }

    /*@Override
    public Student getStudent(String studentId) {
        try {
            return studentsRepository.findById(studentId).get();
        }catch(NoSuchElementException e){
            return null;
        }
    }*/
    @Override
    public ResponseEntity<?> getStudent(String studentId){
        Optional<Student> optionalCont = studentsRepository.findById(studentId);
        if(optionalCont.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Студент с Id " + studentId + " Отсуствует в базе данных");
        else {
            Student student = optionalCont.get();
            return ResponseEntity.ok(student);
        }
    }
    @Override
    public List<Student> getAllStudent() {
        return studentsRepository.findAll();
    }
}
