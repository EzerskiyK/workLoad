package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.EducationalDiscipline;
import com.universityProject.workLoad.model.GroupOfStudents;
import com.universityProject.workLoad.model.Schedule;
import com.universityProject.workLoad.model.Teacher;
import com.universityProject.workLoad.secvices.EducationDisciplineService;
import com.universityProject.workLoad.secvices.GroupOfStudentsService;
import com.universityProject.workLoad.secvices.ScheduleService;
import com.universityProject.workLoad.secvices.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final TeacherService teacherService;
    private final GroupOfStudentsService groupOfStudentsService;
    private final EducationDisciplineService educationDisciplineService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, TeacherService teacherService, GroupOfStudentsService groupOfStudentsService, EducationDisciplineService educationDisciplineService) {
        this.scheduleService = scheduleService;
        this.teacherService = teacherService;
        this.groupOfStudentsService = groupOfStudentsService;
        this.educationDisciplineService = educationDisciplineService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("schedules", scheduleService.findAll());
        return "schedule/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("schedule", scheduleService.findById(id));
        return "schedule/show";
    }

    @GetMapping("/new")
    public String newSubject(@ModelAttribute Schedule schedule,
                             @ModelAttribute("group")GroupOfStudents groupOfStudents,
                             Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("educationalDisciplines", educationDisciplineService.findAll());
        model.addAttribute("groups", groupOfStudentsService.findAll());


        return "schedule/new";
    }

    @PostMapping()
    public String createSubject(@ModelAttribute Schedule schedule,
                                @ModelAttribute("group")GroupOfStudents groupOfStudents) {
        System.out.println(schedule.toString());
        System.out.println(groupOfStudents.toString());

        scheduleService.save(schedule, groupOfStudents);
        return "redirect:/schedule";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       @ModelAttribute("group")GroupOfStudents groupOfStudents,
                       Model model) {
        model.addAttribute("schedule", scheduleService.findById(id));
        model.addAttribute("teachers", teacherService.findAll());
        return "schedule/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("schedule") Schedule schedule,
                         @PathVariable("id") int id) {
        scheduleService.update(id,schedule);
        return "redirect:/schedule/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        scheduleService.delete(id);
        return "redirect:/schedule";
    }

}
