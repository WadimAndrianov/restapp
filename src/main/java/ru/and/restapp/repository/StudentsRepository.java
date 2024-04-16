package ru.and.restapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.and.restapp.model.Student;

import java.util.List;

public interface StudentsRepository extends JpaRepository<Student, String> {
    @Query("SELECT s FROM Student s WHERE (:age IS NULL OR s.age = :age) AND (:email IS NULL OR s.email LIKE CONCAT('%', :email))")
    List<Student> findByParam(@Param("age") Integer age, @Param("email") String domain);

}

