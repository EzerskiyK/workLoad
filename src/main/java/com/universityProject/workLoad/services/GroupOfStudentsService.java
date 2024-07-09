package com.universityProject.workLoad.services;

import com.universityProject.workLoad.model.GroupOfStudents;
import com.universityProject.workLoad.repositories.GroupOfStudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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
        return groupOfStudentsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(GroupOfStudents groupOfStudents) {
        groupOfStudentsRepository.save(groupOfStudents);
    }

    @Transactional
    public void update(int id, GroupOfStudents updatedGroupOfStudents) {

        enrichUpdatedGroupOfStudents(updatedGroupOfStudents, id);

        groupOfStudentsRepository.save(updatedGroupOfStudents);
    }

    @Transactional
    public void delete(int id) {
        groupOfStudentsRepository.deleteById(id);
    }

    private void enrichUpdatedGroupOfStudents(GroupOfStudents updatedGroupOfStudents, int id) {
        GroupOfStudents toUpdateGroupOfStudents = groupOfStudentsRepository
                .findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Group of students not found"));
        updatedGroupOfStudents.setGroupId(toUpdateGroupOfStudents.getGroupId());
        updatedGroupOfStudents.setSubGropOfStudents(toUpdateGroupOfStudents.getSubGropOfStudents());
    }
}
