package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.AcademicDegree;
import com.universityProject.workLoad.model.GroupOfStudents;
import com.universityProject.workLoad.secvices.GroupOfStudentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/group")
public class GroupOfStudentsController {
    private final GroupOfStudentsService groupOfStudentsService;

    @Autowired
    public GroupOfStudentsController(GroupOfStudentsService groupOfStudentsService) {
        this.groupOfStudentsService = groupOfStudentsService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("groups", groupOfStudentsService.findAll());
        return "group/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("group", groupOfStudentsService.findById(id));
        model.addAttribute("subGroups", groupOfStudentsService.findSubGroupsByGroupId(id));
        return "group/show";
    }

    @GetMapping("/new")
    public String newGroup(@ModelAttribute GroupOfStudents groupOfStudents) {
        return "group/new";
    }

    @PostMapping()
    public String createGroup(@ModelAttribute @Valid GroupOfStudents groupOfStudents,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "group/new";
        }
        groupOfStudentsService.save(groupOfStudents);
        return "redirect:/group";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("group", groupOfStudentsService.findById(id));
        return "group/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("group") @Valid GroupOfStudents groupOfStudents,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "group/edit";
        }
        groupOfStudentsService.update(id,groupOfStudents);
        return "redirect:/group/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        groupOfStudentsService.delete(id);
        return "redirect:/group";
    }
}
