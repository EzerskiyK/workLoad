package com.universityProject.workLoad.repositories;

import com.universityProject.workLoad.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByFioContainsIgnoreCaseOrderByFio(String fio);
}
