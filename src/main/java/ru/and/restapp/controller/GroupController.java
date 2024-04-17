package ru.and.restapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.and.restapp.cache.CacheManager;
import ru.and.restapp.exceptions.MyExceptionNotFound;
import ru.and.restapp.model.Group;
import ru.and.restapp.dto.GroupDTO;
import ru.and.restapp.model.Student;
import ru.and.restapp.dto.StudentDTO;
import ru.and.restapp.service.GroupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    CacheManager cache;
    GroupService groupService;

    public GroupController(GroupService groupService, CacheManager cache) {
        this.groupService = groupService;
        this.cache = cache;
    }

    @GetMapping("{groupId}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable("groupId") String groupId) {
        Optional<GroupDTO> optionalGroupDTO = cache.getGroupDTOfromCache(groupId);
        if (optionalGroupDTO.isEmpty()) {
            Optional<Group> optionalGroup = groupService.getGroupById(groupId);
            if (optionalGroup.isEmpty()) {
                throw new MyExceptionNotFound("A Group with this Id was not found");
            } else {
                Group group = optionalGroup.get();
                List<StudentDTO> studentDTOList = new ArrayList<>();
                List<Student> studentList = group.getStudentList();

                for (Student student : studentList) {
                    StudentDTO studentDTO = new StudentDTO(student.getId(), student.getFirstName(),
                            student.getLastName(), student.getEmail(), student.getAge(), groupId);
                    studentDTOList.add(studentDTO);
                }
                GroupDTO groupDTO = new GroupDTO(group.getGroupId(), group.getCuratorName(), studentDTOList);
                cache.addGroupDTOtoCache(groupId, groupDTO);
                return ResponseEntity.ok(groupDTO);
            }
        } else {
            return ResponseEntity.ok(optionalGroupDTO.get());
        }
    }

    @GetMapping("/cache")
    public List<String> getGroupOnlyFromCache() {
        return cache.getAllGroupCache();
    }

    @GetMapping()
    public List<GroupDTO> getAllGroup() {
        return groupService.getAllGroup();
    }

    @PostMapping
    String createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        return groupService.createGroup(groupDTO);
    }

    @PutMapping
    String updateGroup(@Valid @RequestBody GroupDTO groupDTO) {
        cache.removeGroupDTOfromCache(groupDTO.getGroupId()); //удаляем с кэша
        return groupService.updateGroup(groupDTO);
    }

    @DeleteMapping("{groupId}")
    public String deleteGroup(@PathVariable("groupId") String groupId) {
        cache.removeGroupDTOfromCache(groupId); //удаляем с кэша
        return groupService.deleteGroup(groupId);
    }

}
