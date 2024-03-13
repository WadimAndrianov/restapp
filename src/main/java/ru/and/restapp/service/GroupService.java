package ru.and.restapp.service;

import ru.and.restapp.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    public Optional<Group> getGroupById(String groupId);

    public List<Group> getAllGroup();

    public String createGroup(Group group);

    public String updateGroup(Group group);

    public String deleteGroup(String groupId);
}
