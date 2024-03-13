package ru.and.restapp.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.and.restapp.model.Group;
import ru.and.restapp.model.Student;
import ru.and.restapp.repository.GroupRepository;
import ru.and.restapp.repository.StudentsRepository;
import ru.and.restapp.service.GroupService;

import java.util.ArrayList;
import java.util.List;
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
    public List<Group> getAllGroup() {
        return groupRepository.findAll();
    }

    @Override
    public String createGroup(Group group) {
        Optional<Group> optionalGroup = groupRepository.findById(group.getGroupId());
        if (optionalGroup.isEmpty()) {
            groupRepository.save(group);
            List<Student> existsStudent = new ArrayList<>();
            if (group.getStudentList() != null) {
                List<Student> StudentList = group.getStudentList();
                for (Student student : StudentList) {
                 //   Optional<Student> optionalStudent = studentRepository.findById(student.getStudentId());
                    //if (optionalStudent.isEmpty()) {
                        student.setGroup(group);
                        studentRepository.save(student);
                   // } else {
                      //  existsStudent.add(student);
                    //}
                }
            }
          //  if (existsStudent.isEmpty())
                return "Group " + group.getGroupId() + " has been successfully created";
          //  else {
            //    String str = "Group " + group.getGroupId() + " has been successfully created\n" +
             //           "Students who were not included in the group due to the presence of such students in the database\n";
            //    for (Student student : existsStudent) {
             //       str += student.getStudentId() + '\n';
             //   }
             //   return str;
         //   }
        } else {
            return "Failed operation. Group with id " + group.getGroupId() + " already exists";
        }

    }

    /*
 Optional<Group> optionalGroup = groupRepository.findById(group.getGroupId());
        if (optionalGroup.isEmpty()) {
            groupRepository.save(group);
            List<Student> existsStudent = new ArrayList<>();
            if (group.getStudentList() != null) {
                List<Student> StudentList = group.getStudentList();
                for (Student student : StudentList) {
                    Optional<Student> optionalStudent = studentRepository.findById(student.getStudentId());
                    if (optionalStudent.isEmpty()) {
                        student.setGroup(group);
                        studentRepository.save(student);
                    } else {
                        existsStudent.add(student);
                    }
                }
            }
            if (existsStudent.isEmpty())
                return "Group " + group.getGroupId() + " has been successfully created";
            else {
                String str = "Group " + group.getGroupId() + " has been successfully created\n" +
                        "Students who were not included in the group due to the presence of such students in the database\n";
                for(Student student : existsStudent){
                    str += student.getStudentId() + '\n';
                }
                return str;
            }
        } else {
            return "Failed operation. Group with id " + group.getGroupId() + " already exists";
        }

    */
    @Override
    public String updateGroup(Group group) {
        Optional<Group> optionalGroup = groupRepository.findById(group.getGroupId());
        if (optionalGroup.isEmpty()) {
            return "This Group is not in the database";
        } else {
            groupRepository.save(group);
            return "Group updated successful";
        }
    }

    @Override
    public String deleteGroup(String groupId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isEmpty()) {
            return "This Group is not in the database";
        } else {
            groupRepository.deleteById(groupId);
            return "Group has been successfully deleted";
        }
    }
}
