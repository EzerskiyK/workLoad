package com.universityProject.workLoad.services;

import com.universityProject.workLoad.model.Student;
import com.universityProject.workLoad.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class StudentService {

    private StudentRepository studentRepository;

    List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    Student getStudentById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
    }

    void saveStudent(Student student) {
        studentRepository.save(student);
    }

    void deleteStudentById(Integer id) {
        studentRepository.deleteById(id);
    }

    void updateStudent(Student updatedStudent, Integer id) {
        studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
        updatedStudent.setId(id);
        studentRepository.save(updatedStudent);
    }

}
