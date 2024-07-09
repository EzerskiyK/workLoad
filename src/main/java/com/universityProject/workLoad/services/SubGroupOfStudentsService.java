package com.universityProject.workLoad.services;


import com.universityProject.workLoad.model.SubGroupOfStudents;
import com.universityProject.workLoad.repositories.SubGroupOfStudentsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SubGroupOfStudentsService {
    private final SubGroupOfStudentsRepository subGroupOfStudentsRepository;

    public SubGroupOfStudentsService(SubGroupOfStudentsRepository subGroupOfStudentsRepository) {
        this.subGroupOfStudentsRepository = subGroupOfStudentsRepository;
    }

    public List<SubGroupOfStudents> findAll() {
        return subGroupOfStudentsRepository.findAll(Sort.by("subGroupName"));
    }
    public SubGroupOfStudents findById(int id) {
        Optional<SubGroupOfStudents> subGroupOfStudents = subGroupOfStudentsRepository.findById(id);
        return subGroupOfStudents.orElse(null);
    }

    public Optional<SubGroupOfStudents> findByName(String name){
        return subGroupOfStudentsRepository.findBySubGroupName(name);
    }

    public void delete(int id) {
        subGroupOfStudentsRepository.deleteById(id);
    }
}
