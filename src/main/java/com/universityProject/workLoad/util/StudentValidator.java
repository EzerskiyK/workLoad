package com.universityProject.workLoad.util;

import com.universityProject.workLoad.model.EducationalDiscipline;
import com.universityProject.workLoad.model.Student;
import com.universityProject.workLoad.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class StudentValidator implements Validator {

    private final StudentService studentService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Student.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Student student = (Student) target;

        if (!studentService.getStudentByFIO(student.getFio()).isEmpty()) {
            errors.rejectValue("fio", null, "FIO is already in use");
        }
    }
}
