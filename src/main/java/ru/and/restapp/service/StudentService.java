package ru.and.restapp.service;


import ru.and.restapp.model.Student;
import ru.and.restapp.dto.StudentDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public String createStudent(StudentDTO studentDTO);

    public String createStudents(List<StudentDTO> studentDTOlist);

    public String updateStudent(StudentDTO studentDTO);

    public String deleteStudent(String studentId);

    public Optional<Student> getStudent(String studentId);

    public List<StudentDTO> getStudents(Integer age, String email);

}
