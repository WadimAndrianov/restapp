package ru.and.restapp.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class GroupDTO {
    @NotBlank
    private String groupId;
    @NotBlank
    private String curatorName;
    private List<StudentDTO> studentList;

    public GroupDTO(String groupId, String curatorName, List<StudentDTO> studentList) {
        this.groupId = groupId;
        this.curatorName = curatorName;
        this.studentList = studentList;
    }

    public GroupDTO() {
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCuratorName() {
        return curatorName;
    }

    public void setCuratorName(String curatorName) {
        this.curatorName = curatorName;
    }

    public List<StudentDTO> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentDTO> studentList) {
        this.studentList = studentList;
    }

}
