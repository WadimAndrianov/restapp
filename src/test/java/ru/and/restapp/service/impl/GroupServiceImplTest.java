package ru.and.restapp.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.and.restapp.dto.GroupDTO;
import ru.and.restapp.dto.StudentDTO;
import ru.and.restapp.exceptions.MyExceptionBadRequest;
import ru.and.restapp.exceptions.MyExceptionNotFound;
import ru.and.restapp.model.Group;
import ru.and.restapp.model.Student;
import ru.and.restapp.repository.GroupRepository;
import ru.and.restapp.repository.StudentsRepository;
import ru.and.restapp.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class GroupServiceImplTest {

    @Mock
    private StudentsRepository studentsRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    AutoCloseable autoCloseable;
    @BeforeEach
    void setUp() {
        groupService = new GroupServiceImpl(groupRepository, studentsRepository);
        autoCloseable = MockitoAnnotations.openMocks(this);
    }
    //
    @AfterEach
    void tearDown() throws Exception {
        if (autoCloseable != null) {
            autoCloseable.close();
        }
    }


    @Test
    void testCreateGroup_Success() {
        List<StudentDTO> studentDTOlist = new ArrayList<>();
        StudentDTO studentDTO = new StudentDTO("1", "John", "Dae", "example@email.com", 18,"group1");
        studentDTOlist.add(studentDTO);
        GroupDTO groupDTO = new GroupDTO("group1", "Curator", studentDTOlist);
        when(groupRepository.findById("group1")).thenReturn(Optional.empty());
        when(studentsRepository.findById("1")).thenReturn(Optional.of(new Student()));
        when(studentsRepository.findById("2")).thenReturn(Optional.of(new Student()));

        String result = groupService.createGroup(groupDTO);

        assertEquals("Group group1 has been successfully created", result);
        verify(studentsRepository, times(1)).findById(anyString());
    }

    @Test
    void testCreateGroup_GroupAlreadyExists() {

        GroupDTO groupDTO = new GroupDTO("group1", "Curator", new ArrayList<>());
        when(groupRepository.findById("group1")).thenReturn(Optional.of(new Group()));

        MyExceptionBadRequest exception = assertThrows(MyExceptionBadRequest.class, () -> groupService.createGroup(groupDTO));
        assertEquals("Group with id group1 already exists", exception.getMessage());
    }

    @Test
    void testUpdateGroup_Success() {
        List<StudentDTO> studentDTOList = new ArrayList<>();
        studentDTOList.add(new StudentDTO("1", "John", "Doe", "john@example.com", 20, "group1"));
        GroupDTO groupDTO = new GroupDTO("group1", "New Curator", studentDTOList);

        Group existingGroup = new Group("group1", "Old Curator", null);
        existingGroup.setStudentList(new ArrayList<>());
        when(groupRepository.findById("group1")).thenReturn(Optional.of(existingGroup));
        when(studentsRepository.findById("1")).thenReturn(Optional.of(new Student()));

        String result = groupService.updateGroup(groupDTO);

        assertEquals("Group updated successful", result);
        assertEquals("New Curator", existingGroup.getCuratorName());
        verify(groupRepository).save(existingGroup);
    }

    @Test
    void testUpdateGroup_GroupNotFound() {
        GroupDTO groupDTO = new GroupDTO("group1", "New Curator", new ArrayList<>());
        when(groupRepository.findById("group1")).thenReturn(Optional.empty());

        MyExceptionBadRequest exception = assertThrows(MyExceptionBadRequest.class, () -> groupService.updateGroup(groupDTO));
        assertEquals("Group with id group1 not found", exception.getMessage());
    }
    @Test
    void testDeleteGroup_Success() {
        Group existingGroup = new Group("group1", "Curator", new ArrayList<>());
        when(groupRepository.findById("group1")).thenReturn(Optional.of(existingGroup));

        String result = groupService.deleteGroup("group1");

        assertEquals("Group has been successfully deleted", result);
        verify(groupRepository).deleteById("group1");
    }

    @Test
    void testDeleteGroup_GroupNotFound() {
        // Arrange
        when(groupRepository.findById("group1")).thenReturn(Optional.empty());

        // Act & Assert
        MyExceptionNotFound exception = assertThrows(MyExceptionNotFound.class, () -> groupService.deleteGroup("group1"));
        assertEquals("A Group with this Id was not found", exception.getMessage());
    }

    @Test
    void testGetGroupById_GroupExists() {

        Group existingGroup = new Group("group1", "Curator", null);
        when(groupRepository.findById("group1")).thenReturn(Optional.of(existingGroup));

        Optional<Group> result = groupService.getGroupById("group1");

        assertEquals(Optional.of(existingGroup), result);
    }

    @Test
    void testGetGroupById_GroupNotFound() {

        when(groupRepository.findById("group1")).thenReturn(Optional.empty());

        Optional<Group> result = groupService.getGroupById("group1");

        assertEquals(Optional.empty(), result);
    }

    @Test
    void testGetAllGroup() {
        Group group = new Group("group1", "Curator", new ArrayList<>());

        List<Student> students = new ArrayList<>();
        Student student = new Student("John", "Doe", "john@example.com", "1", 20, group);

        students.add(student);
        group.setStudentList(students);

        List<Group> groups = new ArrayList<>();
        groups.add(group);
        when(groupRepository.findAll()).thenReturn(groups);

        List<GroupDTO> result = groupService.getAllGroup();

        assertEquals(1, result.size());
        GroupDTO groupDTO = result.get(0);
        assertEquals("group1", groupDTO.getGroupId());
        assertEquals("Curator", groupDTO.getCuratorName());
        assertEquals(1, groupDTO.getStudentList().size());

        StudentDTO studentDTO = groupDTO.getStudentList().get(0);
        assertEquals("1", studentDTO.getStudentId());
        assertEquals("John", studentDTO.getFirstName());
        assertEquals("Doe", studentDTO.getLastName());
        assertEquals("john@example.com", studentDTO.getEmail());
        assertEquals(20, studentDTO.getAge());
        assertEquals("group1", studentDTO.getGroupId());
    }

}