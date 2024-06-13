package com.universityProject.workLoad.util;

import com.universityProject.workLoad.model.EducationalDiscipline;
import com.universityProject.workLoad.services.EducationDisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EducationDisciplineValidator implements Validator {
    private final EducationDisciplineService educationDisciplineService;

    @Autowired
    public EducationDisciplineValidator(EducationDisciplineService educationDisciplineService) {
        this.educationDisciplineService = educationDisciplineService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return EducationalDiscipline.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EducationalDiscipline ed = (EducationalDiscipline) target;
        if(ed.getEducationalDisciplineId() == null){
            if(educationDisciplineService.findByEducationalDisciplineName(ed.getEducationalDisciplineName()).isPresent()){
                errors.rejectValue("educationalDisciplineName", "", "Предмет с таким названием уже существует");
            }
        }else{
            EducationalDiscipline educationDisciplineToUpdate = educationDisciplineService.findById(ed.getEducationalDisciplineId());
            if(educationDisciplineToUpdate != null && !educationDisciplineToUpdate.getEducationalDisciplineName().equals(ed.getEducationalDisciplineName())
            && educationDisciplineService.findByEducationalDisciplineName(ed.getEducationalDisciplineName()).isPresent()){
                errors.rejectValue("educationalDisciplineName", "", "Предмет с таким названием уже существует");
            }
        }
    }
}
