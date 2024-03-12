package ru.and.restapp.model;


import jakarta.persistence.*;

@Entity
@Table(name = "students_info")
public class Student {
    @Id
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private int age;

    //@ManyToOne(cascade = CascadeType.ALL)
    //private Group group;
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;


    public Student() {
    }
    public Student(String firstName, String lastName, String email, String studentId, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentId = studentId;
        this.age = age;
    }
    public Student(String firstName, String lastName, String email, String studentId, int age, Group group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentId = studentId;
        this.age = age;
        this.group = group;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
