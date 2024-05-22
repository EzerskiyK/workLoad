package com.universityProject.workLoad.secvices;

import com.universityProject.workLoad.model.EducationalDiscipline;
import com.universityProject.workLoad.model.Schedule;
import com.universityProject.workLoad.model.SubGroupOfStudents;
import com.universityProject.workLoad.repositories.SubGroupOfStudentsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<EducationalDiscipline> findSubjectsBySubGroupId(int subGroupId) {
        Optional<SubGroupOfStudents> subGroupOfStudents = subGroupOfStudentsRepository.findById(subGroupId);
        if (subGroupOfStudents.isPresent()) {
            List<EducationalDiscipline> listOfSubjects = new ArrayList<>();
            List<Schedule> listOfSchedule = subGroupOfStudents.get().getScheduleList();
            for (Schedule schedule : listOfSchedule) {
                listOfSubjects.add(schedule.getEducationalDiscipline());
            }

            return listOfSubjects;
        }else{
            return Collections.emptyList();
        }
    }

    public Optional<SubGroupOfStudents> findByName(String name){
        return subGroupOfStudentsRepository.findBySubGroupName(name);
    }

    public void delete(int id) {
        subGroupOfStudentsRepository.deleteById(id);
    }
}
