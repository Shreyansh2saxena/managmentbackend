package com.example.Management.controller;
import com.example.Management.entity.Student;
import com.example.Management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
@RequestMapping("api/students")
public class StudentController {

    @Autowired
    private StudentService service;

//    @PostMapping
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
//    public Student createStudent(@RequestBody Student student) {
//        return service.saveStudent(student);
//    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER'')")
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    public Student getStudentById(@PathVariable Long id) {
        return service.getStudentById(id).orElse(null);
    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
//    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
//        return service.updateStudent(id, student);
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    public void deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
    }

    @PostMapping("/saveOrUpdate")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    public Student createOrUpdateStudent(@RequestBody Student student) {
        return service.saveOrUpdateStudentByEmail(student);
    }

}
