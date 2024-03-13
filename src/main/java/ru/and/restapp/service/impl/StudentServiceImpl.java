package ru.and.restapp.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.and.restapp.model.Group;
import ru.and.restapp.model.Student;
import ru.and.restapp.model.StudentDTO;
import ru.and.restapp.repository.GroupRepository;
import ru.and.restapp.repository.StudentsRepository;
import ru.and.restapp.service.GroupService;
import ru.and.restapp.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    StudentsRepository studentsRepository;
    GroupRepository groupsRepository;
    public StudentServiceImpl(StudentsRepository studentsRepository, GroupRepository groupsRepository) {
        this.studentsRepository = studentsRepository;
        this.groupsRepository = groupsRepository;
    }

    @Override
    public String createStudent(Student student) {
        Optional<Student> optionalStudent = studentsRepository.findById(student.getStudentId());
        if (optionalStudent.isEmpty()) {
            Optional<Group> optionalGroup = groupsRepository.findById(student.getGroup().getGroupId());
            if(optionalGroup.isEmpty()){
                return "Failed operation. Group with id " + student.getGroup().getGroupId() + " not exist";
            }else{
                studentsRepository.save(student);
                return "Student has been successfully created";
            }
        } else {
            return "Failed operation. Student with id " + student.getStudentId() + " already exists";
        }

    }

    @Override
    public String updateStudent(Student student) {
        Optional<Student> optionalStudent = studentsRepository.findById(student.getStudentId());
        if (optionalStudent.isEmpty()) {
            return "This student is not in the database";
        } else {
            Optional<Group> optionalGroup = groupsRepository.findById(student.getGroup().getGroupId());
            if(optionalGroup.isEmpty()){
                return "Failed operation. Group with id " + student.getGroup().getGroupId() + " not exist";
            }else {
                studentsRepository.save(student);
                return "Student updated successful";
            }
        }
    }

    @Override
    public String deleteStudent(String studentId) {
        Optional<Student> optionalStudent = studentsRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            return "This student is not in the database";
        } else {
            studentsRepository.deleteById(studentId);
            return "Student has been successfully deleted";
        }
    }

    @Override
    public List<Student> getAllStudent() {
        return studentsRepository.findAll();
    }

    @Override
    public Optional<Student> getStudent(String studentId) {
        return studentsRepository.findById(studentId);
    }
}
