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

import java.util.ArrayList;
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
    public String createStudent(StudentDTO studentDTO) {
        Optional<Student> optionalStudent = studentsRepository.findById(studentDTO.getStudentId());
        if (optionalStudent.isEmpty()) {
            Optional<Group> optionalGroup = groupsRepository.findById(studentDTO.getGroupId());
            if(optionalGroup.isEmpty()){
                return "Failed operation. Group with id " + studentDTO.getGroupId() + " not exist";
            }else{
                //String firstName, String lastName, String email, String studentId, int age, Group group
                Student student = new Student(studentDTO.getFirstName(), studentDTO.getLastName(),
                studentDTO.getEmail(), studentDTO.getStudentId(), studentDTO.getAge(), optionalGroup.get());
                studentsRepository.save(student);
                return "Student has been successfully created";
            }
        } else {
            return "Failed operation. Student with id " + studentDTO.getStudentId() + " already exists";
        }

    }

    @Override
    public String updateStudent(StudentDTO studentDTO) {
        Optional<Student> optionalStudent = studentsRepository.findById(studentDTO.getStudentId());
        if (optionalStudent.isEmpty()) {
            return "This student is not in the database";
        } else {
            Optional<Group> optionalGroup = groupsRepository.findById(studentDTO.getGroupId());
            if(optionalGroup.isEmpty()){
                return "Failed operation. Group with id " + studentDTO.getGroupId() + " not exist";
            }else {
                Student student = new Student(studentDTO.getFirstName(), studentDTO.getLastName(),
                        studentDTO.getEmail(), studentDTO.getStudentId(), studentDTO.getAge(), optionalGroup.get());
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
    public List<StudentDTO> getAllStudent() {
        List<Student> ListStudents = studentsRepository.findAll();
        List<StudentDTO> ListStudentDTO = new ArrayList<>();
        for(Student student : ListStudents){
            //String studentId, String firstName, String lastName, String email, int age, String groupId
            StudentDTO studentDTO = new StudentDTO(student.getStudentId(), student.getFirstName(),
            student.getLastName(), student.getEmail(), student.getAge(), student.getGroup().getGroupId());
            ListStudentDTO.add(studentDTO);
        }
        return ListStudentDTO;
    }

    @Override
    public Optional<Student> getStudent(String studentId) {
        return studentsRepository.findById(studentId);
    }
}
