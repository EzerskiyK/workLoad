package com.universityProject.workLoad.repositories;

import com.universityProject.workLoad.model.GroupOfStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupOfStudentsRepository extends JpaRepository<GroupOfStudents, Integer> {
}
