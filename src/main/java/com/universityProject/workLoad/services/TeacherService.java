package com.universityProject.workLoad.services;

import com.universityProject.workLoad.model.Teacher;
import com.universityProject.workLoad.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TeacherService {

    @Value("${teacher.image.upload.path}")
    private String teacherImageUploadPath;

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> findAll() {
        return teacherRepository.findAll(Sort.by("fio"));
    }

    public Teacher findById(int id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Teacher teacher, MultipartFile file) throws IOException {

        if(!file.isEmpty()){
            teacher.setImageFileName(saveImage(file));
        }

        teacherRepository.save(teacher);
    }

    @Transactional
    public void update(int id, Teacher updatedTeacher, MultipartFile file) throws IOException {
        enrichUpdatedTeacher(updatedTeacher, id, file);

        teacherRepository.save(updatedTeacher);
    }

    @Transactional
    public void delete(int id) {
        File file = new File(teacherImageUploadPath + "/" + teacherRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not find teacher with id: " + id))
                .getImageFileName());
        if(file.delete()){
            System.out.println("deleted");
        } else {
            System.out.println("not deleted");
        }
        teacherRepository.deleteById(id);
    }

    public String saveImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            File uploadDir = new File(teacherImageUploadPath);
            if (!uploadDir.exists()) {
                System.out.println(uploadDir.mkdirs());
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(teacherImageUploadPath+ "/" + resultFileName));
            return resultFileName;
        }
        return null;
    }

    public List<Teacher> searchTeacher(String query) {
        if(query != null) {
            return teacherRepository.findByFioContainsIgnoreCaseOrderByFio(query);
        }
        return findAll();
    }

    public Boolean teachersHasOverwork() {
        return teacherRepository.findAll().stream().anyMatch(teacher -> teacher.getActualWorkingHours()>teacher.getMaximumWorkingHours());
    }

    private void enrichUpdatedTeacher(Teacher updatedTeacher, int id, MultipartFile file) throws IOException {
        Teacher teacherToUpdate = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        updatedTeacher.setTeacherId(teacherToUpdate.getTeacherId());
        double maximumWorkingHours = updatedTeacher.getWorkingRate()
                * updatedTeacher.getAcademicDegree().getWorkLimit();
        updatedTeacher.setMaximumWorkingHours(maximumWorkingHours);
        updatedTeacher.setActualSubject(teacherToUpdate.getActualSubject());
        updatedTeacher.setActualWorkingHours(teacherToUpdate.getActualWorkingHours());

        if(file.isEmpty()){
            updatedTeacher.setImageFileName(teacherToUpdate.getImageFileName());
        } else {
            updatedTeacher.setImageFileName(saveImage(file));
        }
    }

}
