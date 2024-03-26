package ru.and.restapp.repository;

import jakarta.persistence.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.and.restapp.model.Student;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface StudentsRepository extends JpaRepository<Student, String> {
    //"Select * from students_info where students_info.age = :age"

    @Query("SELECT s FROM Student s WHERE (:age IS NULL OR s.age = :age) AND (:email IS NULL OR s.email LIKE CONCAT('%', :email))")
    List<Student> findByParam(@Param("age") Integer age, @Param("email") String domain);

}

