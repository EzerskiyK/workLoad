package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.AcademicDegree;
import com.universityProject.workLoad.secvices.AcademicDegreeService;
import com.universityProject.workLoad.util.AcademicDegreeValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/academic_degree")
public class AcademicDegreeController {

    private final AcademicDegreeService academicDegreeService;
    private final AcademicDegreeValidator academicDegreeValidator;


    @Autowired
    public AcademicDegreeController(AcademicDegreeService academicDegreeService, AcademicDegreeValidator academicDegreeValidator) {
        this.academicDegreeService = academicDegreeService;
        this.academicDegreeValidator = academicDegreeValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("academicDegrees", academicDegreeService.findAll());
        return "academic_degree/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("academicDegree", academicDegreeService.findById(id));
        model.addAttribute("teachers", academicDegreeService.findTeachersByAcademicDegreeId(id));
        return "academic_degree/show";
    }

    @GetMapping("/new")
    public String newAcademicDegree(@ModelAttribute AcademicDegree academicDegree) {
        return "academic_degree/new";
    }

    @PostMapping()
    public String createAcademicDegree(@ModelAttribute @Valid AcademicDegree academicDegree,
                                       BindingResult bindingResult) {
        academicDegreeValidator.validate(academicDegree, bindingResult);
        if (bindingResult.hasErrors()) {
            return "academic_degree/new";
        }
        academicDegreeService.save(academicDegree);
        return "redirect:/academic_degree";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("academicDegree", academicDegreeService.findById(id));
        return "academic_degree/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("academicDegree") @Valid AcademicDegree academicDegree,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        academicDegreeValidator.validate(academicDegree, bindingResult);
        if (bindingResult.hasErrors()) {
            return "academic_degree/edit";
        }
        academicDegreeService.update(id,academicDegree);
        return "redirect:/academic_degree/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        academicDegreeService.delete(id);
        return "redirect:/academic_degree";
    }
}
