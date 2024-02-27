package ru.and.restapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.and.restapp.model.Student;

public interface StudentsRepository extends JpaRepository<Student, String> {

}

