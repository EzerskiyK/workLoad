package com.universityProject.workLoad.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MenuNavigationController {

    @GetMapping()
    public String startMenu() {
        return "menu_navigation/startMenu";
    }

}
