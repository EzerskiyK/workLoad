package com.universityProject.workLoad.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sub_group_of_student")
public class SubGroupOfStudents {

    @Id
    @Column(name = "sub_group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subGroupOfStudentId;

    @Column(name = "name")
    private String subGroupName;

    @ManyToOne
    @JoinColumn(name = "main_group", referencedColumnName = "group_id")
    private GroupOfStudents mainGroup;

    @Column(name = "number_of_students")
    private int numberOfStudents;

    @OneToMany(mappedBy = "subGroupOfStudents")
    private List<Schedule> scheduleList;


    public SubGroupOfStudents() {
    }

    public SubGroupOfStudents(String subGroupName,GroupOfStudents mainGroup, int numberOfStudents1) {
        this.subGroupName = subGroupName;
        this.mainGroup = mainGroup;
        this.numberOfStudents = numberOfStudents1;
    }

    public void setSubGroupOfStudentId(int subGroupOfStudentId) {
        this.subGroupOfStudentId = subGroupOfStudentId;
    }

    public GroupOfStudents getMainGroup() {
        return mainGroup;
    }

    public void setMainGroup(GroupOfStudents mainGroup) {
        this.mainGroup = mainGroup;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getSubGroupOfStudentId() {
        return subGroupOfStudentId;
    }



    public String getSubGroupName() {
        return subGroupName;
    }

    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public void addSchedule(Schedule schedule) {
        if(scheduleList == null) {
            scheduleList = new ArrayList<>();
            scheduleList.add(schedule);
        }else{
            scheduleList.add(schedule);
        }

    }
}
