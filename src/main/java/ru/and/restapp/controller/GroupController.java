package ru.and.restapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.and.restapp.model.Cache.CacheManager;
import ru.and.restapp.model.Group;
import ru.and.restapp.model.GroupDTO;
import ru.and.restapp.model.Student;
import ru.and.restapp.model.StudentDTO;
import ru.and.restapp.service.GroupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    CacheManager cache;
    GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @GetMapping("{groupId}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable("groupId") String groupId) {
        Optional<GroupDTO> optionalGroupDTO = cache.getGroupDTOfromCache(groupId);
        if(optionalGroupDTO.isEmpty()) {
            Optional<Group> optionalGroup = groupService.getGroupById(groupId);
            if (optionalGroup.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                Group group = optionalGroup.get();
                List<StudentDTO> studentDTOList = new ArrayList<>();
                List<Student> studentList = group.getStudentList();

                for (Student student : studentList) {
                    StudentDTO studentDTO = new StudentDTO(student.getStudentId(), student.getFirstName(),
                            student.getLastName(), student.getEmail(), student.getAge(), groupId);
                    studentDTOList.add(studentDTO);
                }
                GroupDTO groupDTO = new GroupDTO(group.getGroupId(), group.getMonitorName(), studentDTOList);
                return ResponseEntity.ok(groupDTO);
            }
        }else{
            return ResponseEntity.ok(optionalGroupDTO.get());
        }
    }

    @GetMapping()
    public List<GroupDTO> getAllGroup() {
        return groupService.getAllGroup();
    }

    @PostMapping
    String createGroup(@RequestBody GroupDTO groupDTO) {
        return groupService.createGroup(groupDTO);
    }

    @PutMapping
    String updateGroup(@RequestBody GroupDTO groupDTO) {
        cache.removeGroupDTOfromCache(groupDTO.getGroupId()); //удаляем с кэша
        return groupService.updateGroup(groupDTO);
    }

    @DeleteMapping("{groupId}")
    public String deleteGroup(@PathVariable("groupId") String groupId) {
        cache.removeGroupDTOfromCache(groupId);//удаляем с кэша
        return groupService.deleteGroup(groupId);
    }

}
