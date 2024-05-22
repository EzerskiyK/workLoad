package com.universityProject.workLoad.util;

import com.universityProject.workLoad.model.AcademicDegree;
import com.universityProject.workLoad.secvices.AcademicDegreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AcademicDegreeValidator implements Validator {
    private final AcademicDegreeService academicDegreeService;

    @Autowired
    public AcademicDegreeValidator(AcademicDegreeService academicDegreeService) {
        this.academicDegreeService = academicDegreeService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return AcademicDegree.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AcademicDegree academicDegree = (AcademicDegree) target;
        if (academicDegree.getAcademicDegreeId() == null){
            if(academicDegreeService.findAcademicDegreeByName(academicDegree.getAcademicDegreeName()).isPresent()){
                errors.rejectValue("academicDegreeName", "", "Должность с тамким названием уже существует");
            }
        }else{
            AcademicDegree academicDegreeToUpdate = academicDegreeService.findById(academicDegree.getAcademicDegreeId());
            if(academicDegreeToUpdate != null && !academicDegreeToUpdate.getAcademicDegreeName().equals(academicDegree.getAcademicDegreeName())
            && academicDegreeService.findAcademicDegreeByName(academicDegree.getAcademicDegreeName()).isPresent()){
                errors.rejectValue("academicDegreeName", "", "Должность с тамким названием уже существует cerf");

            }
        }




    }
}
