package com.universityProject.workLoad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "student")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    private Integer id;

    @Column(name = "fio")
    @NotNull(message = "Это поле не должно быть пустым")
    @Size(min = 6, message = "Это поле должно содержать минимум 6 символов")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "Поле заполнено не верно, формат заполнения: Фамилия Имя Отчество")
    private String fio;

    @NotNull(message = "Это поле не должно быть пустым")
    @Column(name = "date_of_admission")
    private LocalDate dateOfAdmission;

    @NotNull(message = "Это поле не должно быть пустым")
    @Column(name = "grade")
    private Integer grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "group_of_students", referencedColumnName = "group_id")
    private GroupOfStudents groupOfStudents;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "sub_group", referencedColumnName = "sub_group_id")
    private SubGroupOfStudents subGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "scientific_supervisor", referencedColumnName = "teacher_id")
    private Teacher scientificSupervisor;
}