package ru.and.restapp.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.and.restapp.exceptions.MyExceptionBadRequest;
import ru.and.restapp.exceptions.MyExceptionNotFound;
import ru.and.restapp.model.Group;
import ru.and.restapp.dto.GroupDTO;
import ru.and.restapp.model.Student;
import ru.and.restapp.dto.StudentDTO;
import ru.and.restapp.repository.GroupRepository;
import ru.and.restapp.repository.StudentsRepository;
import ru.and.restapp.service.GroupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    GroupRepository groupRepository;
    StudentsRepository studentRepository;

    public GroupServiceImpl(GroupRepository groupRepository, StudentsRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Group> getGroupById(String groupId) {
        return groupRepository.findById(groupId);
    }

    @Override
    public List<GroupDTO> getAllGroup() {
        List<Group> listGroup = groupRepository.findAll();
        List<GroupDTO> listGroupDTO = new ArrayList<>();

        for (Group group : listGroup) {
            List<Student> listStudent = group.getStudentList();
            List<StudentDTO> listStudentDTO = new ArrayList<>();

            for (Student student : listStudent) {
                StudentDTO studentDTO = new StudentDTO(student.getId(), student.getFirstName(),
                        student.getLastName(), student.getEmail(), student.getAge(), student.getGroup().getGroupId());
                listStudentDTO.add(studentDTO);
            }
            GroupDTO groupDTO = new GroupDTO(group.getGroupId(), group.getCuratorName(), listStudentDTO);
            listGroupDTO.add(groupDTO);
        }
        return listGroupDTO;
    }

    @Override
    public String createGroup(GroupDTO groupDTO) {
        Optional<Group> optionalGroup = groupRepository.findById(groupDTO.getGroupId());
        if (optionalGroup.isPresent()) {
            throw new MyExceptionBadRequest("Group with id " + groupDTO.getGroupId() + " already exists");
        } else {
            Group group = new Group(groupDTO.getGroupId(), groupDTO.getCuratorName(), null);
            if (groupDTO.getStudentList() != null) {
                List<StudentDTO> studentDTOList = groupDTO.getStudentList();
                List<Student> studentList = new ArrayList<>();
                for (StudentDTO studentDTO : studentDTOList) {
                    Optional<Student> optionalStudent = studentRepository.findById(studentDTO.getStudentId());

                    if (optionalStudent.isEmpty() || optionalStudent.get().getGroup() == null) {
                        Student student = new Student(studentDTO.getFirstName(), studentDTO.getLastName(),
                                studentDTO.getEmail(), studentDTO.getStudentId(), studentDTO.getAge(), group);
                        studentList.add(student);
                    }
                }
                if (!studentList.isEmpty()) {
                    group.setStudentList(studentList);
                }
            }
            groupRepository.save(group);
            return "Group " + group.getGroupId() + " has been successfully created";
        }
    }

    @Override
    public String updateGroup(GroupDTO groupDTO) {
        Optional<Group> optionalGroup = groupRepository.findById(groupDTO.getGroupId());
        if (optionalGroup.isEmpty()) {
            throw new MyExceptionBadRequest("Group with id " + groupDTO.getGroupId() + " not found");
        } else {
            Group group = optionalGroup.get();
            group.setCuratorName(groupDTO.getCuratorName());
            List<StudentDTO> studentDTOList = groupDTO.getStudentList();
            List<Student> studentList = new ArrayList<>();
            for (StudentDTO studentDTO : studentDTOList) {
                Optional<Student> optionalStudent = studentRepository.findById(studentDTO.getStudentId());

                if (optionalStudent.isEmpty() || optionalStudent.get().getGroup() == null
                        || Objects.equals(optionalStudent.get().getGroup().getGroupId(), groupDTO.getGroupId())) {
                    Student student = new Student(studentDTO.getFirstName(), studentDTO.getLastName(),
                            studentDTO.getEmail(), studentDTO.getStudentId(), studentDTO.getAge(), group);
                    studentList.add(student);
                }

            }
            for (Student student : group.getStudentList()) {
                student.setGroup(null);
            }

            group.setStudentList(studentList);
            groupRepository.save(group);

            return "Group updated successful";
        }
    }

    @Override
    public String deleteGroup(String groupId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isEmpty()) {
            throw new MyExceptionNotFound("A Group with this Id was not found");
        } else {
            List<Student> studentList = optionalGroup.get().getStudentList();
            for (Student student : studentList) {
                student.setGroup(null);
            }
            optionalGroup.get().setStudentList(null);
            groupRepository.deleteById(groupId);
            return "Group has been successfully deleted";
        }
    }
}
