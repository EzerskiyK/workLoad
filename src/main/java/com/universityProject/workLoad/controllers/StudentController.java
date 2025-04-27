package com.universityProject.workLoad.controllers;

import com.universityProject.workLoad.model.AcademicDegree;
import com.universityProject.workLoad.model.Student;
import com.universityProject.workLoad.services.GroupOfStudentsService;
import com.universityProject.workLoad.services.StudentService;
import com.universityProject.workLoad.services.TeacherService;
import com.universityProject.workLoad.util.StudentValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/settings/student")
@Slf4j
public class StudentController {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final GroupOfStudentsService groupOfStudentsService;
    private final StudentValidator studentValidator;

    @GetMapping()
    public String index(@RequestParam(name = "query", required = false) String query,
                               @ModelAttribute Student student,
                               Model model){
        model.addAttribute("students", studentService.getStudentByFIO(query));
        model.addAttribute("groupOfStudents", groupOfStudentsService.findAll());
        return "student/index";
    }

    @PostMapping("/create")
    public String createStudent(@ModelAttribute("student") @Valid Student student,
                                BindingResult bindingResult,
                                Model model){
        studentValidator.validate(student, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            model.addAttribute("students", studentService.getStudentByFIO(null));
            model.addAttribute("groupOfStudents", groupOfStudentsService.findAll());
            return "student/index";
        }

        studentService.saveStudent(student);
        return "redirect:/settings/student";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {

        model.addAttribute("student", studentService.getStudentById(id));
        model.addAttribute("groupOfStudents", groupOfStudentsService.findAll());
        model.addAttribute("teachers", teacherService.findAll());

        return "student/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("student") @Valid Student student,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "student/edit";
        }

        studentService.updateStudent(student, id);

        return "redirect:/settings/student";
    }

    @DeleteMapping()
    public String delete(@RequestParam("studentId") int id) {

        studentService.deleteStudentById(id);

        return "redirect:/settings/academic_degree";
    }
}
