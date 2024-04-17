package ru.and.restapp.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "groups_")
public class Group {
    @Id
    private String groupId;

    private String curatorName;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Student> studentList;

    public Group() {
    }

    public Group(String groupId, String curatorName, List<Student> studentList) {
        this.groupId = groupId;
        this.curatorName = curatorName;
        this.studentList = studentList;
    }

    public String getCuratorName() {
        return curatorName;
    }

    public void setCuratorName(String curatorName) {
        this.curatorName = curatorName;
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
