package ru.and.restapp.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.and.restapp.repository.GroupRepository;
import ru.and.restapp.repository.StudentsRepository;
import ru.and.restapp.service.StudentService;


@DataJpaTest
class GroupServiceImplTest {

    @Mock
    private StudentsRepository studentsRepository;

    @Mock
    private GroupRepository groupRepository;

    private StudentService studentService;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetGroupById() {
    }

    @Test
    void testGetAllGroup() {
    }

    @Test
    void testCreateGroup() {
    }

    @Test
    void testUpdateGroup() {
    }

    @Test
    void testDeleteGroup() {
    }
}