package com.universityProject.workLoad.repositories;

import com.universityProject.workLoad.model.EducationalDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducationDisciplineRepository extends JpaRepository<EducationalDiscipline, Integer> {
    public Optional<EducationalDiscipline> findByEducationalDisciplineName(String disciplineName);
}
