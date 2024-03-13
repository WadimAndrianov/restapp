package ru.and.restapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.and.restapp.model.Group;
import ru.and.restapp.service.GroupService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("{groupId}")
    public ResponseEntity<Group> getGroup(@PathVariable("groupId") String groupId) {
        Optional<Group> optionalGroup = groupService.getGroupById(groupId);
        if (optionalGroup.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(optionalGroup.get());
        }
    }

    @GetMapping()
    public List<Group> getAllGroup() {
        return groupService.getAllGroup();
    }

    @PostMapping
    String createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }

    @PutMapping
    String updateGroup(@RequestBody Group group) {
        return groupService.updateGroup(group);
    }

    @DeleteMapping("{groupId}")
    public String deleteGroup(@PathVariable("groupId") String groupId) {
        return groupService.deleteGroup(groupId);
    }

}
