package com.example.Management.repository;

import com.example.Management.entity.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    List<CourseEnrollment> findByStudentId(Long studentId);
}