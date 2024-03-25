package ru.and.restapp.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "group_info")
public class Group {
    @Id
    private String groupId;

    private  String monitorName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Student> studentList;
    public Group() {
    }

    public Group(String groupId, String monitorName, List<Student> studentList) {
        this.groupId = groupId;
        this.monitorName = monitorName;
        this.studentList = studentList;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
