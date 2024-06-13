package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.GroupOfStudents;
import com.universityProject.workLoad.services.GroupOfStudentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/settings/group")
public class GroupOfStudentsController {
    private final GroupOfStudentsService groupOfStudentsService;

    @Autowired
    public GroupOfStudentsController(GroupOfStudentsService groupOfStudentsService) {
        this.groupOfStudentsService = groupOfStudentsService;
    }

    @GetMapping()
    public String index(@ModelAttribute GroupOfStudents group,Model model) {
        model.addAttribute("groups", groupOfStudentsService.findAll());
        return "group/index";
    }

    @PostMapping()
    public String createGroup(@ModelAttribute @Valid GroupOfStudents groupOfStudents,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "group/index";
        }

        groupOfStudentsService.save(groupOfStudents);

        return "redirect:/settings/group";
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

        return "redirect:/settings/group";
    }

    @DeleteMapping()
    public String delete(@RequestParam("groupId") int id) {
        groupOfStudentsService.delete(id);
        return "redirect:/settings/group";
    }
}
