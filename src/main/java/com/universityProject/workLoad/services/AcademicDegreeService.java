package com.universityProject.workLoad.services;

import com.universityProject.workLoad.model.AcademicDegree;
import com.universityProject.workLoad.repositories.AcademicDegreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

        AcademicDegree academicDegreeToUpdate = academicDegreeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid academic degree"));

        enrichUpdatedAcademicDegree(updatedAcademicDegree, academicDegreeToUpdate);

        academicDegreeRepository.save(updatedAcademicDegree);
    }

    @Transactional
    public void delete(int id) {
        academicDegreeRepository.findById(id).ifPresent(
                a -> a.getTeacherList()
                        .forEach(teacher -> teacher.setMaximumWorkingHours(1)));
        academicDegreeRepository.deleteById(id);

    }

    public Optional<AcademicDegree> findAcademicDegreeByName(String name) {
        return academicDegreeRepository.findByAcademicDegreeName(name);
    }

    private void enrichUpdatedAcademicDegree(AcademicDegree updatedAcademicDegree,
                                             AcademicDegree academicDegreeToUpdate) {

        updatedAcademicDegree.setAcademicDegreeId(academicDegreeToUpdate.getAcademicDegreeId());

        updatedAcademicDegree.setTeacherList(academicDegreeToUpdate.getTeacherList());

        updatedAcademicDegree.getTeacherList()
                .forEach(teacher -> teacher.setMaximumWorkingHours(updatedAcademicDegree.getWorkLimit()));
    }
}
