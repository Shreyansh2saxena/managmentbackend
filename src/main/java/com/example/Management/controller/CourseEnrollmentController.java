package com.example.Management.controller;


import com.example.Management.entity.CourseEnrollment;
import com.example.Management.service.CourseEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class CourseEnrollmentController {

    private final CourseEnrollmentService enrollmentService;

    @PostMapping
    public CourseEnrollment createEnrollment(@RequestBody CourseEnrollment enrollment) {
        return enrollmentService.createEnrollment(enrollment);
    }

    @GetMapping
    public List<CourseEnrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @GetMapping("/student/{studentId}")
    public List<CourseEnrollment> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudentId(studentId);
    }

    @GetMapping("/{id}")
    public CourseEnrollment getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id);
    }

    @PutMapping("/{id}")
    public CourseEnrollment updateEnrollment(@PathVariable Long id, @RequestBody CourseEnrollment enrollment) {
        return enrollmentService.updateEnrollment(id, enrollment);
    }

    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
    }
}