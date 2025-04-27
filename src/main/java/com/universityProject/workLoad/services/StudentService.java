package com.universityProject.workLoad.services;

import com.universityProject.workLoad.model.Student;
import com.universityProject.workLoad.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherService teacherService;

    public List<Student> getAllStudents() {
        return studentRepository.findAll(Sort.by("fio"));
    }

    public Student getStudentById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
    }

    public List<Student> getStudentByFIO(String fio) {
        if(fio != null) {
            return studentRepository.findByFioContainsIgnoreCaseOrderByFio(fio);
        }
        return studentRepository.findAll(Sort.by("fio"));
    }

    @Transactional
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudentById(Integer id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Student updatedStudent, Integer id) {
        Student studentToUpdate = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));

        if(!updatedStudent.getScientificSupervisor().equals(studentToUpdate.getScientificSupervisor())) {
            teacherService.editWorkingHoursByStudent(updatedStudent.getScientificSupervisor(), studentToUpdate.getScientificSupervisor());
        }

        updatedStudent.setId(id);
        studentRepository.save(updatedStudent);
    }
}
