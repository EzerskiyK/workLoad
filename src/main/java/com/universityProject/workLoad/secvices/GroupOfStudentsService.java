package com.universityProject.workLoad.secvices;

import com.universityProject.workLoad.model.AcademicDegree;
import com.universityProject.workLoad.model.GroupOfStudents;
import com.universityProject.workLoad.model.SubGroupOfStudents;
import com.universityProject.workLoad.repositories.GroupOfStudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupOfStudentsService {
    private final GroupOfStudentsRepository groupOfStudentsRepository;

    @Autowired
    public GroupOfStudentsService(GroupOfStudentsRepository groupOfStudentsRepository) {
        this.groupOfStudentsRepository = groupOfStudentsRepository;
    }

    public List<GroupOfStudents> findAll() {
        return groupOfStudentsRepository.findAll(Sort.by("speciality"));
    }

    public GroupOfStudents findById(int id) {
        Optional<GroupOfStudents> groupOfStudents = groupOfStudentsRepository.findById(id);
        return groupOfStudents.orElse(null);
    }

    @Transactional
    public void save(GroupOfStudents groupOfStudents) {
        groupOfStudentsRepository.save(groupOfStudents);
    }

    @Transactional
    public void update(int id, GroupOfStudents updatedGroupOfStudents) {
        GroupOfStudents toUpdateGroupOfStudents = groupOfStudentsRepository.findById(id).get();
        updatedGroupOfStudents.setGroupId(toUpdateGroupOfStudents.getGroupId());
        updatedGroupOfStudents.setSubGropOfStudents(toUpdateGroupOfStudents.getSubGropOfStudents());

        groupOfStudentsRepository.save(updatedGroupOfStudents);
    }

    @Transactional
    public void delete(int id) {
        groupOfStudentsRepository.deleteById(id);
    }

    public List<SubGroupOfStudents> findSubGroupsByGroupId(int groupId) {
        Optional<GroupOfStudents> groupOfStudents = groupOfStudentsRepository.findById(groupId);
        if(groupOfStudents.isPresent()) {
            return groupOfStudents.get().getSubGropOfStudents();
        }
        return Collections.emptyList();
    }
}
