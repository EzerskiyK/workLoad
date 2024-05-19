package com.universityProject.workLoad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teacherId;

    @Column(name = "fio")
    @NotNull(message = "Это поле не должно быть пустым")
    @Size(min = 6, message = "Это поле должно содержать минимум 6 символов")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "Поле заполнено не верно, формат заполнения: Фамилия Имя Отчество")
    private String fio;

    @ManyToOne()
    @JoinColumn(name = "academic_degree", referencedColumnName = "id")
    @NotNull(message = "Это поле не должно быть пустым")
    private AcademicDegree academicDegree;

    @Column(name = "working_rate")
    @NotNull(message = "Это поле не должно быть пустым")
    @Min(value = 0, message = "Это поле должно быть больше 0")
    private double workingRate;

    @Column(name = "max_work_hours")
    private double maximumWorkingHours;

    @Column(name="actual_working_hours")
    private double actualWorkingHours;

    @Column(name = "image_file_name")
    private String imageFileName;

    @OneToMany(mappedBy = "recommendedTeacher")
    private List<EducationalDiscipline> recommendedSubject;

    @OneToMany(mappedBy = "actualTeacher")
    private List<Schedule> actualSubject;

    @PrePersist
    private void init(){
        System.out.println(getFio());
        System.out.println(getAcademicDegree().toString());
        maximumWorkingHours = workingRate * (double) getAcademicDegree().getWorkLimit();
    }


    public Teacher() {}

    public Teacher(String fio, AcademicDegree academicDegree, double workingRate) {
        this.fio = fio;
        this.academicDegree = academicDegree;
        this.workingRate = workingRate;
    }

    public double getMaximumWorkingHours() {
        return maximumWorkingHours;
    }

    public void setMaximumWorkingHours(double maximumWorkingHours) {
        this.maximumWorkingHours = maximumWorkingHours;
    }

    public double getWorkingRate() {
        return workingRate;
    }

    public void setWorkingRate(double workingRate) {
        this.workingRate = workingRate;
    }

    public AcademicDegree getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(AcademicDegree academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public List<EducationalDiscipline> getRecommendedSubject() {
        return recommendedSubject;
    }

    public void setRecommendedSubject(List<EducationalDiscipline> recommendedSubject) {
        this.recommendedSubject = recommendedSubject;
    }

    public double getActualWorkingHours() {
        return actualWorkingHours;
    }

    public void setActualWorkingHours(double actualWorkingHours) {
        this.actualWorkingHours = actualWorkingHours;
    }

    public List<Schedule> getActualSubject() {
        return actualSubject;
    }

    public void setActualSubject(List<Schedule> actualSubject) {
        this.actualSubject = actualSubject;
    }

    public void addActualWorkingHours(double actualWorkingHours) {
        this.actualWorkingHours += actualWorkingHours;
    }

    public void subtractActualWorkingHours(double actualWorkingHours) {
        this.actualWorkingHours -= actualWorkingHours;
    }
}
