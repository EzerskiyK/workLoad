package com.universityProject.workLoad.aspect;

import com.universityProject.workLoad.services.ScheduleService;
import com.universityProject.workLoad.services.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Collection;

@Aspect
@Component
public class NavbarAspect {
    private final ScheduleService scheduleService;
    private final TeacherService teacherService;

    @Autowired
    public NavbarAspect(ScheduleService scheduleService, TeacherService teacherService) {
        this.scheduleService = scheduleService;
        this.teacherService = teacherService;
    }

    @Before("execution(* com.universityProject.workLoad.controllers.*.*(..))")
    private void checkSchedule(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Boolean notAllScheduleHasTeacher  = scheduleService.notAllSubjectHaveATeacher();
        request.setAttribute("notAllScheduleHasTeacher", notAllScheduleHasTeacher);
    }

    @Before("execution(* com.universityProject.workLoad.controllers.*.*(..))")
    private void checkScheduleTeacher(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Boolean teachersHasOverwork  = teacherService.teachersHasOverwork();
        request.setAttribute("teachersHasOverwork", teachersHasOverwork);
    }

}
