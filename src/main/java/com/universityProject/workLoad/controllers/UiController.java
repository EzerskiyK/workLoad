package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.EducationalDiscipline;
import com.universityProject.workLoad.model.GroupOfStudents;
import com.universityProject.workLoad.model.Schedule;
import com.universityProject.workLoad.model.Teacher;
import com.universityProject.workLoad.secvices.EducationDisciplineService;
import com.universityProject.workLoad.secvices.GroupOfStudentsService;
import com.universityProject.workLoad.secvices.ScheduleService;
import com.universityProject.workLoad.secvices.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;

@Controller
@RequestMapping("/")
public class UiController {
    private final TeacherService teacherService;
    private final GroupOfStudentsService groupOfStudentsService;
    private final EducationDisciplineService educationDisciplineService;
    private final ScheduleService scheduleService;


    public UiController(TeacherService teacherService, GroupOfStudentsService groupOfStudentsService, EducationDisciplineService educationDisciplineService, ScheduleService scheduleService) {
        this.teacherService = teacherService;
        this.groupOfStudentsService = groupOfStudentsService;
        this.educationDisciplineService = educationDisciplineService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule_teacher")
    public String teacherIndex(Model model){
        model.addAttribute("teachers", teacherService.findAll());
        return "schedule/schedule_teacher";
    }

    @GetMapping("/schedule_teacher/{id}")
    public String teacherShow(@PathVariable("id") int id,
                              Model model){
        model.addAttribute("teacher", teacherService.findById(id));
        model.addAttribute("groupOfStudents", groupOfStudentsService.findAll());
        model.addAttribute("educationDisciplines", educationDisciplineService.findAll());

        return "schedule/schedule_teacher_show";

    }

    @PostMapping("/schedule_teacher/{id}")
    public String createSchedule(@RequestParam("groupOfStudentsId") int groupOfStudentsId,
                                 @PathVariable("id") int teacherId,
                                 @RequestParam("subjectId") int subjectId){
        Teacher teacher = teacherService.findById(teacherId);
        GroupOfStudents groupOfStudents = groupOfStudentsService.findById(groupOfStudentsId);
        EducationalDiscipline educationalDiscipline = educationDisciplineService.findById(subjectId);
        Schedule schedule = new Schedule(teacher, educationalDiscipline);
        scheduleService.save(schedule, groupOfStudents);
        return "redirect:/schedule_teacher/" + teacherId;
    }
}
