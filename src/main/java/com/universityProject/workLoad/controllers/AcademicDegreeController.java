package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.AcademicDegree;
import com.universityProject.workLoad.services.AcademicDegreeService;
import com.universityProject.workLoad.util.AcademicDegreeValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/settings/academic_degree")
public class AcademicDegreeController {

    private final AcademicDegreeService academicDegreeService;
    private final AcademicDegreeValidator academicDegreeValidator;


    @Autowired
    public AcademicDegreeController(AcademicDegreeService academicDegreeService, AcademicDegreeValidator academicDegreeValidator) {
        this.academicDegreeService = academicDegreeService;
        this.academicDegreeValidator = academicDegreeValidator;
    }

    @GetMapping()
    public String index(@ModelAttribute AcademicDegree academicDegree, Model model) {

        model.addAttribute("academicDegrees", academicDegreeService.findAll());

        return "academic_degree/index";
    }

    @PostMapping()
    public String createAcademicDegree(@ModelAttribute @Valid AcademicDegree academicDegree,
                                       BindingResult bindingResult, Model model) {

        academicDegreeValidator.validate(academicDegree, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("academicDegrees", academicDegreeService.findAll());
            return "/academic_degree/index";
        }

        academicDegreeService.save(academicDegree);

        return "redirect:/settings/academic_degree";
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

        return "redirect:/settings/academic_degree";
    }

    @DeleteMapping()
    public String delete(@RequestParam("academicDegreeId") int id) {

        academicDegreeService.delete(id);

        return "redirect:/settings/academic_degree";
    }

}
