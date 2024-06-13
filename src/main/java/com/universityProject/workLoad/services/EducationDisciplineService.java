package com.universityProject.workLoad.services;

import com.universityProject.workLoad.model.EducationalDiscipline;
import com.universityProject.workLoad.repositories.EducationDisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        Optional<EducationalDiscipline> educationalDisciplineToUpdate = educationDisciplineRepository.findById(id);

        updatedDiscipline.setEducationalDisciplineId(educationalDisciplineToUpdate.get()
                    .getEducationalDisciplineId());

        educationDisciplineRepository.save(updatedDiscipline);

    }
    @Transactional
    public void delete(int id) {
        educationDisciplineRepository.deleteById(id);
    }

    public Optional<EducationalDiscipline> findByEducationalDisciplineName(String educationalDisciplineName) {
        return educationDisciplineRepository.findByEducationalDisciplineName(educationalDisciplineName);
    }
}
