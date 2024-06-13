package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.GroupOfStudents;
import com.universityProject.workLoad.model.Schedule;
import com.universityProject.workLoad.services.EducationDisciplineService;
import com.universityProject.workLoad.services.GroupOfStudentsService;
import com.universityProject.workLoad.services.ScheduleService;
import com.universityProject.workLoad.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/main/schedule")
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
    public String index(@ModelAttribute Schedule schedule,
                        @ModelAttribute("group")GroupOfStudents groupOfStudents,
                        Model model) {

        model.addAttribute("schedules", scheduleService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("educationalDisciplines", educationDisciplineService.findAll());
        model.addAttribute("groups", groupOfStudentsService.findAll());

        return "schedule/index";
    }
    @PostMapping()
    public String createSubject(@ModelAttribute Schedule schedule,
                                @ModelAttribute("group")GroupOfStudents groupOfStudents) {
        scheduleService.save(schedule, groupOfStudents);
        return "redirect:/main/schedule";
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
        return "redirect:/main/schedule";
    }

    @DeleteMapping()
    public String delete(@RequestParam("scheduleId") int id) {
        scheduleService.delete(id);
        return "redirect:/main/schedule";
    }

}
