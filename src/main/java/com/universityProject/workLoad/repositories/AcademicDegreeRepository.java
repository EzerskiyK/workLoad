package com.universityProject.workLoad.repositories;

import com.universityProject.workLoad.model.AcademicDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicDegreeRepository extends JpaRepository<AcademicDegree, Integer> {
    public Optional<AcademicDegree> findByAcademicDegreeName(String name);
}
