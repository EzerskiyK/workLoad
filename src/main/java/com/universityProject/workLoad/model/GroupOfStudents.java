package com.universityProject.workLoad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Entity
@Table(name = "group_of_students")
public class GroupOfStudents {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    @Column(name = "speciality")
    @NotNull(message = "Это поле не должно быть пустым")
    @Size(min = 1, message = "Название специальность дожно быть длинной минимум 1 символ")
    private String speciality;

    @Column(name = "year_of_entering")
    @NotNull(message = "Это поле не должно быть пустым")
    @Min(value = 2015, message = "Год появления группы не должно быть раньше 2015 года")
    private int yearOfEntering;


    @Column(name = "number_of_students")
    @NotNull(message = "Это поле не должно быть пустым")
    @Min(value = 1, message = "Количество студентов должно быть больше 0")
    private int numberOfStudents;

    @OneToMany(mappedBy = "mainGroup")
    private List<SubGroupOfStudents> subGroupOfStudents;



    ///////////////////////

    public GroupOfStudents() {}

    public GroupOfStudents(String speciality, int yearOfEntering, int numberOfStudents) {
        this.speciality = speciality;
        this.yearOfEntering = yearOfEntering;
        this.numberOfStudents = numberOfStudents;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getYearOfEntering() {
        return yearOfEntering;
    }

    public void setYearOfEntering(int curriculum) {
        this.yearOfEntering = curriculum;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public List<SubGroupOfStudents> getSubGropOfStudents() {
        return subGroupOfStudents;
    }

    public void setSubGropOfStudents(List<SubGroupOfStudents> subGroupOfStudents) {
        this.subGroupOfStudents = subGroupOfStudents;
    }

}
