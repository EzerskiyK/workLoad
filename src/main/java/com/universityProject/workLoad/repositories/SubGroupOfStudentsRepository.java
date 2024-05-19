package com.universityProject.workLoad.repositories;

import com.universityProject.workLoad.model.SubGroupOfStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubGroupOfStudentsRepository extends JpaRepository<SubGroupOfStudents, Integer> {
    public Optional<SubGroupOfStudents> findBySubGroupName(String subGroupName);
}
