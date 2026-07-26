package com.universityProject.workLoad.controllers.api;

import com.universityProject.workLoad.services.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InfoController {

    private final TeacherService teacherService;

    @GetMapping("/teacher_count")
    public ResponseEntity<Long> getTeacherCount() {
        log.info("Get teacher count");
        return ResponseEntity.ok(teacherService.getCount());
    }
}
