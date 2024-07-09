package com.universityProject.workLoad.services;

import com.universityProject.workLoad.model.EducationalDiscipline;
import com.universityProject.workLoad.model.Schedule;
import com.universityProject.workLoad.model.Teacher;
import com.universityProject.workLoad.repositories.EducationDisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EducationDisciplineService {

    private final EducationDisciplineRepository educationDisciplineRepository;

    @Autowired
    public EducationDisciplineService(EducationDisciplineRepository educationDisciplineRepository) {
        this.educationDisciplineRepository = educationDisciplineRepository;
    }

    public List<EducationalDiscipline> findAll(){
        return educationDisciplineRepository.findAll(Sort.by("educationalDisciplineName"));
    }

    public EducationalDiscipline findById(int id) {
        Optional<EducationalDiscipline> educationalDiscipline = educationDisciplineRepository.findById(id);
        return educationalDiscipline.orElse(null);
    }

    @Transactional
    public void save(EducationalDiscipline educationalDiscipline) {
        educationDisciplineRepository.save(educationalDiscipline);
    }

    @Transactional
    public void update(int id, EducationalDiscipline updatedDiscipline) {

        enrichUpdatedEducationalDiscipline(updatedDiscipline, id);

        educationDisciplineRepository.save(updatedDiscipline);

        updatedDiscipline.getSchedules().stream()
                .map(Schedule::getActualTeacher)
                .filter(Objects::nonNull)
                .forEach(Teacher::setActualWorkingHours);

    }

    @Transactional
    public void delete(int id) {
        List<Teacher> teachers = educationDisciplineRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid education discipline ID"))
                .getSchedules().stream()
                .map(Schedule::getActualTeacher)
                .filter(Objects::nonNull)
                .toList();

        educationDisciplineRepository.deleteById(id);

        teachers.forEach(teacher -> {
            teacher.deleteSubject(id);
            teacher.setActualWorkingHours();});
    }

    public Optional<EducationalDiscipline> findByEducationalDisciplineName(String educationalDisciplineName) {
        return educationDisciplineRepository.findByEducationalDisciplineName(educationalDisciplineName);
    }

    private void enrichUpdatedEducationalDiscipline(EducationalDiscipline updatedDiscipline,
                                                    int id) {
        EducationalDiscipline educationalDisciplineToUpdate = educationDisciplineRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid education discipline ID"));

        updatedDiscipline.setEducationalDisciplineId(educationalDisciplineToUpdate
                .getEducationalDisciplineId());

        updatedDiscipline.setSchedules(educationalDisciplineToUpdate.getSchedules());

    }
}
