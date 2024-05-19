package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.GroupOfStudents;
import com.universityProject.workLoad.secvices.SubGroupOfStudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sub_group")
public class SubGroupOfStudentsController {
    private final SubGroupOfStudentsService subGroupOfStudentsService;

    @Autowired
    public SubGroupOfStudentsController(SubGroupOfStudentsService subGroupOfStudentsService) {
        this.subGroupOfStudentsService = subGroupOfStudentsService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("subGroups", subGroupOfStudentsService.findAll());
        return "sub_group/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("subGroup", subGroupOfStudentsService.findById(id));
        model.addAttribute("subjects", subGroupOfStudentsService.findSubjectsBySubGroupId(id));
        return "sub_group/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        subGroupOfStudentsService.delete(id);
        return "redirect:/sub_group";
    }
}
