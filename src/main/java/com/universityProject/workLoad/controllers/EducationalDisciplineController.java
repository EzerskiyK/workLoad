package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.AcademicDegree;
import com.universityProject.workLoad.model.EducationalDiscipline;
import com.universityProject.workLoad.model.Teacher;
import com.universityProject.workLoad.secvices.EducationDisciplineService;
import com.universityProject.workLoad.secvices.TeacherService;
import com.universityProject.workLoad.util.EducationDisciplineValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;

@Controller
@RequestMapping("/subject")
public class EducationalDisciplineController {
    private final EducationDisciplineService educationDisciplineService;
    private final TeacherService teacherService;
    private final EducationDisciplineValidator educationDisciplineValidator;

    @Autowired
    public EducationalDisciplineController(EducationDisciplineService educationDisciplineService, TeacherService teacherService, EducationDisciplineValidator educationDisciplineValidator) {
        this.educationDisciplineService = educationDisciplineService;
        this.teacherService = teacherService;
        this.educationDisciplineValidator = educationDisciplineValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("subjects", educationDisciplineService.findAll());
        return "subject/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("subject", educationDisciplineService.findById(id));
        return "subject/show";
    }

    @GetMapping("/new")
    public String newSubject(@ModelAttribute EducationalDiscipline educationalDiscipline,
                             Model model) {
        model.addAttribute("recTeachers", teacherService.findAll());
        return "subject/new";
    }

    @PostMapping()
    public String createSubject(@ModelAttribute @Valid EducationalDiscipline educationalDiscipline,
                                BindingResult bindingResult, Model model) {
        educationDisciplineValidator.validate(educationalDiscipline, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("recTeachers", teacherService.findAll());
            return "subject/new";
        }
        educationDisciplineService.save(educationalDiscipline);
        return "redirect:/subject";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("subject", educationDisciplineService.findById(id));
        model.addAttribute("recTeachers", teacherService.findAll());
        return "subject/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("subject") @Valid EducationalDiscipline educationalDiscipline,
                         BindingResult bindingResult,
                         @PathVariable("id") int id, Model model) {
        educationDisciplineValidator.validate(educationalDiscipline, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("recTeachers", teacherService.findAll());
            return "subject/edit";
        }
        educationDisciplineService.update(id,educationalDiscipline);
        return "redirect:/subject/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        educationDisciplineService.delete(id);
        return "redirect:/subject";
    }
}
