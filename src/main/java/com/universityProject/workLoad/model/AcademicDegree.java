package com.universityProject.workLoad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Entity
@Table(name = "academic_degree")
public class AcademicDegree {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int academicDegreeId;

    @Column(name = "academic_degree_name")
    @NotNull(message = "Это поле не должно быть пустым!")
    @Size(min = 2, message = "Длинна этого поля должна состовлять минимум 2 символа")
    private String academicDegreeName;

    @Column(name = "work_limit")
    @NotNull(message = "Это поле не должно быть пустым!")
    @Min(value = 1, message = "Лимит рабочих часов должен быть больше 0")
    private int workLimit;

    @OneToMany(mappedBy = "academicDegree")
    private List<Teacher> teacherList;

    public AcademicDegree() {}

    public AcademicDegree(String academicDegreeName, int workLimit) {
        this.academicDegreeName = academicDegreeName;
        this.workLimit = workLimit;
    }

    public int getAcademicDegreeId() {
        return academicDegreeId;
    }

    public void setAcademicDegreeId(int academicDegreeId) {
        this.academicDegreeId = academicDegreeId;
    }

    public String getAcademicDegreeName() {
        return academicDegreeName;
    }

    public void setAcademicDegreeName(String academicDegreeName) {
        this.academicDegreeName = academicDegreeName;
    }

    public int getWorkLimit() {
        return workLimit;
    }

    public void setWorkLimit(int workLimit) {
        this.workLimit = workLimit;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    @Override
    public String toString() {
        return "AcademicDegree{" +
                "academicDegreeId=" + academicDegreeId +
                ", academicDegreeName='" + academicDegreeName + '\'' +
                ", workLimit=" + workLimit +
                ", teacherList=" + teacherList +
                '}';
    }
}
