package com.universityProject.workLoad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scheduleId;

    @ManyToOne
    @JoinColumn(name = "sub_group_id", referencedColumnName = "sub_group_id")
    private SubGroupOfStudents subGroupOfStudents;

    @ManyToOne
    @JoinColumn(name = "teacher", referencedColumnName = "teacher_id")
    private Teacher actualTeacher;

    @ManyToOne
    @JoinColumn(name = "education_discipline_id", referencedColumnName = "education_discipline_id")
    private EducationalDiscipline educationalDiscipline;

    public Schedule() {
    }

    public Schedule(SubGroupOfStudents subGroupOfStudents, EducationalDiscipline educationalDiscipline, Teacher actualTeacher) {
        this.subGroupOfStudents = subGroupOfStudents;
        this.educationalDiscipline = educationalDiscipline;
        this.actualTeacher = actualTeacher;
    }

    public Schedule(Teacher actualTeacher, EducationalDiscipline educationalDiscipline) {
        this.actualTeacher = actualTeacher;
        this.educationalDiscipline = educationalDiscipline;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public SubGroupOfStudents getSubGroupOfStudents() {
        return subGroupOfStudents;
    }

    public void setSubGroupOfStudents(SubGroupOfStudents subGroupOfStudents) {
        this.subGroupOfStudents = subGroupOfStudents;
    }

    public Teacher getActualTeacher() {
        return actualTeacher;
    }

    public void setActualTeacher(Teacher actualTeacher) {
        this.actualTeacher = actualTeacher;
    }

    public EducationalDiscipline getEducationalDiscipline() {
        return educationalDiscipline;
    }

    public void setEducationalDiscipline(EducationalDiscipline educationalDiscipline) {
        this.educationalDiscipline = educationalDiscipline;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", subGroupOfStudents=" + subGroupOfStudents +
                ", actualTeacher=" + actualTeacher.getFio() +
                ", educationalDiscipline=" + educationalDiscipline.getEducationalDisciplineName() +
                '}';
    }
}
