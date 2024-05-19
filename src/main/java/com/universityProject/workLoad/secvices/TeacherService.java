package com.universityProject.workLoad.secvices;

import com.universityProject.workLoad.model.EducationalDiscipline;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.orElse(null);
    }

    @Transactional
    public void save(Teacher teacher, MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            System.out.println("Соси");
            teacher.setImageFileName(saveImage(file));
        }

        teacherRepository.save(teacher);
    }

    @Transactional
    public void update(int id, Teacher updatedTeacher, MultipartFile file) throws IOException {

        Teacher teacherToUpdate = teacherRepository.findById(id).get();
        updatedTeacher.setTeacherId(teacherToUpdate.getTeacherId());
        double maximumWorkingHours = updatedTeacher.getWorkingRate() * updatedTeacher.getAcademicDegree().getWorkLimit();
        updatedTeacher.setMaximumWorkingHours(maximumWorkingHours);

        if(file.isEmpty()){
            System.out.println(1);
            updatedTeacher.setImageFileName(teacherToUpdate.getImageFileName());
        } else {
            System.out.println(2);
            updatedTeacher.setImageFileName(saveImage(file));
        }

        teacherRepository.save(updatedTeacher);
    }

    @Transactional
    public void delete(int id) {
        File file = new File(teacherImageUploadPath + "/" + teacherRepository.findById(id).get().getImageFileName());
        if(file.delete()){
            System.out.println("deleted");
        } else {
            System.out.println("not deleted");
        }
        teacherRepository.deleteById(id);
    }

    public List<EducationalDiscipline> findRecSubjectByTeacherId(int id){
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(teacher.isPresent()){
            return teacher.get().getRecommendedSubject();
        }else
            return Collections.emptyList();
    }

    public String saveImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            File uploadDir = new File(teacherImageUploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
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
}
