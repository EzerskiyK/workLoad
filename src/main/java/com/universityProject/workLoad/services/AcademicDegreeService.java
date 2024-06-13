package com.universityProject.workLoad.services;

import com.universityProject.workLoad.model.AcademicDegree;
import com.universityProject.workLoad.model.Teacher;
import com.universityProject.workLoad.repositories.AcademicDegreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AcademicDegreeService {
    private final AcademicDegreeRepository academicDegreeRepository;

    @Autowired
    public AcademicDegreeService(AcademicDegreeRepository academicDegreeRepository) {
        this.academicDegreeRepository = academicDegreeRepository;
    }

    public List<AcademicDegree> findAll() {
        return academicDegreeRepository.findAll(Sort.by("academicDegreeName"));
    }

    public AcademicDegree findById(int id) {
        Optional<AcademicDegree> academicDegree = academicDegreeRepository.findById(id);
        return academicDegree.orElse(null);
    }

    @Transactional
    public void save(AcademicDegree academicDegree) {
        academicDegreeRepository.save(academicDegree);
    }

    @Transactional
    public void update(int id,AcademicDegree updatedAcademicDegree) {

        AcademicDegree academicDegreeToUpdate = academicDegreeRepository.findById(id).get();
        updatedAcademicDegree.setAcademicDegreeId(academicDegreeToUpdate.getAcademicDegreeId());
        updatedAcademicDegree.setTeacherList(academicDegreeToUpdate.getTeacherList());
        academicDegreeRepository.save(updatedAcademicDegree);
    }

    @Transactional
    public void delete(int id) {
        academicDegreeRepository.deleteById(id);
    }


    public List<Teacher> findTeachersByAcademicDegreeId(int id) {

        Optional<AcademicDegree> academicDegree = academicDegreeRepository.findById(id);

        if(academicDegree.isPresent()) {
            return academicDegree.get().getTeacherList();
        }else {
            return Collections.emptyList();
        }
    }

    public Optional<AcademicDegree> findAcademicDegreeByName(String name) {
        return academicDegreeRepository.findByAcademicDegreeName(name);
    }
}
