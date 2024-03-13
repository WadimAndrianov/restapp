package ru.and.restapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.and.restapp.model.Group;

public interface GroupRepository extends JpaRepository<Group, String> {

}
