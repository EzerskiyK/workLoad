package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.AcademicDegree;
import com.universityProject.workLoad.model.Teacher;
import com.universityProject.workLoad.secvices.AcademicDegreeService;
import com.universityProject.workLoad.secvices.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;
    private final AcademicDegreeService academicDegreeService;


    @Autowired
    public TeacherController(TeacherService teacherService, AcademicDegreeService academicDegreeService) {
        this.teacherService = teacherService;
        this.academicDegreeService = academicDegreeService;
    }

    @GetMapping()
    public String index(@ModelAttribute Teacher teacher,Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("academicDegrees", academicDegreeService.findAll());

        return "teacher/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("teacher", teacherService.findById(id));
        model.addAttribute("recSubjects", teacherService.findRecSubjectByTeacherId(id));
        return "teacher/show";
    }

    @GetMapping("/new")
    public String newTeacher(@ModelAttribute Teacher teacher,
                             Model model,
                             @ModelAttribute("imgFile") MultipartFile imgFile ) {
        model.addAttribute("academicDegrees", academicDegreeService.findAll());
        return "teacher/new";
    }

    @PostMapping()
    public String createTeacher(@ModelAttribute @Valid Teacher teacher,
                                BindingResult bindingResult, Model model,
                                @ModelAttribute("imgFile") MultipartFile imgFile) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("academicDegrees", academicDegreeService.findAll());
            model.addAttribute("teachers", teacherService.findAll());

            return "teacher/index";
        }
        teacherService.save(teacher, imgFile);

        return "redirect:/teacher";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model,
                       @ModelAttribute("imgFile") MultipartFile imgFile) {
        model.addAttribute("teacher", teacherService.findById(id));
        model.addAttribute("academicDegrees", academicDegreeService.findAll());
        return "teacher/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("teacher") @Valid Teacher teacher,
                         BindingResult bindingResult,
                         @ModelAttribute("imgFile") MultipartFile imgFile,
                         @PathVariable("id") int id, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("academicDegrees", academicDegreeService.findAll());
            return "teacher/edit";
        }
        System.out.println(id);
        teacherService.update(id,teacher, imgFile);
        return "redirect:/teacher";
    }

    @DeleteMapping()
    public String delete(@RequestParam("teacherId") int id) {
        teacherService.delete(id);
        return "redirect:/teacher";
    }
}
