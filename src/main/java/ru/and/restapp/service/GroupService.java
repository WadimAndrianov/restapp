package ru.and.restapp.service;

import ru.and.restapp.model.Group;
import ru.and.restapp.model.GroupDTO;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    public Optional<Group> getGroupById(String groupId);

    public List<GroupDTO> getAllGroup();

    public String createGroup(GroupDTO group);

    public String updateGroup(GroupDTO group);

    public String deleteGroup(String groupId);
}
