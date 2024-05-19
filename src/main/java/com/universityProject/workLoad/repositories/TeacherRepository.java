package com.universityProject.workLoad.repositories;

import com.universityProject.workLoad.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    public List<Teacher> findByFioContainsIgnoreCaseOrderByFio(String query);


}
