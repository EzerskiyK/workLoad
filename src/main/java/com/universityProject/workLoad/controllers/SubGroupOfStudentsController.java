package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.secvices.SubGroupOfStudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/settings/sub_group")
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

    @DeleteMapping()
    public String delete(@RequestParam("subGroupId") int id) {
        subGroupOfStudentsService.delete(id);
        return "redirect:/settings/sub_group";
    }
}
