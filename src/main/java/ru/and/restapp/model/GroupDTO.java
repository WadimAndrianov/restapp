package ru.and.restapp.model;

import java.util.List;

public class GroupDTO {
    private String groupId;
    private  String monitorName;
    private List<StudentDTO> studentList;
    public GroupDTO(String groupId, String monitorName, List<StudentDTO> studentList) {
        this.groupId = groupId;
        this.monitorName = monitorName;
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

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public List<StudentDTO> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentDTO> studentList) {
        this.studentList = studentList;
    }


}
