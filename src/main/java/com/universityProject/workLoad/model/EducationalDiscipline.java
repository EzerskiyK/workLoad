package com.universityProject.workLoad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "education_discipline")
public class EducationalDiscipline {

    @Id
    @Column(name = "education_discipline_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer educationalDisciplineId;

    @Column(name = "education_discipline_name")
    @NotNull(message = "Это поле не должно быть пустым")
    @Size(min = 1, message = "Название предмета должно состоять минимум из одного символа")
    private String educationalDisciplineName;

    @Column(name = "duration")
    @NotNull(message = "Это поле не должно быть пустым")
    @Min(value = 0, message = "Продолжительность пар должнабыть больше 0")
    private Integer educationalDisciplineDuration;

    @Column(name = "max_students")
    @NotNull(message = "Это поле не должно быть пустым")
    @Min(value = 0, message = "Максимальное число студентов должно быть больше 0")
    private Integer maxStudents;

    @ManyToOne
    @JoinColumn(name = "recommended_teacher", referencedColumnName = "teacher_id")
    @NotNull(message = "Это поле не должно быть пустым")
    private Teacher recommendedTeacher;

    @OneToMany(mappedBy = "educationalDiscipline")
    private List<Schedule> schedules;


    /////////////


    public EducationalDiscipline() {}

    public EducationalDiscipline(String educationalDisciplineName, Integer educationalDisciplineDuration, Teacher recomendedTeacher, Integer maxStudents) {
        this.educationalDisciplineName = educationalDisciplineName;
        this.educationalDisciplineDuration = educationalDisciplineDuration;
        this.recommendedTeacher = recomendedTeacher;
        this.maxStudents = maxStudents;
    }

    public Integer getEducationalDisciplineId() {
        return educationalDisciplineId;
    }

    public void setEducationalDisciplineId(Integer educationalDisciplineId) {
        this.educationalDisciplineId = educationalDisciplineId;
    }

    public String getEducationalDisciplineName() {
        return educationalDisciplineName;
    }

    public void setEducationalDisciplineName(String educationalDisciplineName) {
        this.educationalDisciplineName = educationalDisciplineName;
    }

    public Integer getEducationalDisciplineDuration() {
        return educationalDisciplineDuration;
    }

    public void setEducationalDisciplineDuration(Integer educationalDisciplineDuration) {
        this.educationalDisciplineDuration = educationalDisciplineDuration;
    }


    public Teacher getRecommendedTeacher() {
        return recommendedTeacher;
    }

    public void setRecommendedTeacher(Teacher recommendedTeacher) {
        this.recommendedTeacher = recommendedTeacher;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }
}
