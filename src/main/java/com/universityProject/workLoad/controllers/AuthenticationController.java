package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.Person;
import com.universityProject.workLoad.services.PersonService;
import com.universityProject.workLoad.util.PersonValidator;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    private final PersonService personService;
    private final PersonValidator personValidator;

    @Autowired
    public AuthenticationController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String login(@PathParam(value = "error") String error, Model model) {
        if (error != null) {
            model.addAttribute("error",
                    error.equals("User is disabled") ?
                            "Ваш аккаунт на рассмотрении у администратора" :
                            "Не верный логин или пароль");
        }
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        personService.register(person);
        return "redirect:/auth/login";
    }
}
