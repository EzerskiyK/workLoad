package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.EducationalDiscipline;
import com.universityProject.workLoad.model.GroupOfStudents;
import com.universityProject.workLoad.model.Schedule;
import com.universityProject.workLoad.model.Teacher;
import com.universityProject.workLoad.services.EducationDisciplineService;
import com.universityProject.workLoad.services.GroupOfStudentsService;
import com.universityProject.workLoad.services.ScheduleService;
import com.universityProject.workLoad.services.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/main/schedule_teacher")
public class ScheduleTeacherController {
    private final TeacherService teacherService;
    private final GroupOfStudentsService groupOfStudentsService;
    private final EducationDisciplineService educationDisciplineService;
    private final ScheduleService scheduleService;


    public ScheduleTeacherController(TeacherService teacherService, GroupOfStudentsService groupOfStudentsService, EducationDisciplineService educationDisciplineService, ScheduleService scheduleService) {
        this.teacherService = teacherService;
        this.groupOfStudentsService = groupOfStudentsService;
        this.educationDisciplineService = educationDisciplineService;
        this.scheduleService = scheduleService;
    }

    @GetMapping()
    public String teacherIndex(@RequestParam(name = "query", required = false) String query,
                               Model model){
        model.addAttribute("teachers", teacherService.searchTeacher(query));
        return "schedule_teacher/index";
    }

    @GetMapping("/{id}")
    public String teacherShow(@PathVariable("id") int id,
                              Model model){

        model.addAttribute("teacher", teacherService.findById(id));
        model.addAttribute("groupOfStudents", groupOfStudentsService.findAll());
        model.addAttribute("educationDisciplines", educationDisciplineService.findAll());

        return "schedule_teacher/show";

    }

    @PostMapping("/{id}")
    public String createSchedule(@RequestParam("groupOfStudentsId") int groupOfStudentsId,
                                 @PathVariable("id") int teacherId,
                                 @RequestParam("subjectId") int subjectId){

        Teacher teacher = teacherService.findById(teacherId);
        GroupOfStudents groupOfStudents = groupOfStudentsService.findById(groupOfStudentsId);
        EducationalDiscipline educationalDiscipline = educationDisciplineService.findById(subjectId);

        Schedule schedule = new Schedule(teacher, educationalDiscipline);

        scheduleService.save(schedule, groupOfStudents);
        return "redirect:/main/schedule_teacher/" + teacherId;
    }
}
