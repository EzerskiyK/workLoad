package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping()
    public String admin(Model model) {

        model.addAttribute("users", adminService.getAllPersons());
        return "admin/main";
    }

    @PatchMapping("/activate")
    public String activate(@RequestParam("idToActivate") int idToActivate){
        adminService.activate(idToActivate);
        return "redirect:/admin";
    }

    @PatchMapping("/deactivate")
    public String deactivate(@RequestParam("idToDeactivate") int idToDeactivate){
        adminService.deactivate(idToDeactivate);
        return "redirect:/admin";
    }

}
