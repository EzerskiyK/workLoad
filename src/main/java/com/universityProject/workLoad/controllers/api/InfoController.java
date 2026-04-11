package com.universityProject.workLoad.controllers.api;

import com.universityProject.workLoad.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InfoController {

    private final TeacherService teacherService;

    @GetMapping("/teacher_count")
    public ResponseEntity<Long> getTeacherCount() {
        return ResponseEntity.ok(teacherService.getCount());
    }
}
