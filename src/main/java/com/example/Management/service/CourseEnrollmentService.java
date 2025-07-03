package com.example.Management.service;

import com.example.Management.entity.CourseEnrollment;
import com.example.Management.entity.Student;
import com.example.Management.repository.CourseEnrollmentRepository;
import com.example.Management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentService {

    private final CourseEnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    public CourseEnrollment createEnrollment(CourseEnrollment enrollment) {
        String studentId = enrollment.getStudent().getId();
        Student student = (Student) studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + studentId));
        enrollment.setStudent(student);
        return enrollmentRepository.save(enrollment);
    }

    public List<CourseEnrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public CourseEnrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id " + id));
    }

    public List<CourseEnrollment> getEnrollmentsByStudentId(String studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public CourseEnrollment updateEnrollment(Long id, CourseEnrollment updated) {
        CourseEnrollment existing = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id " + id));

        existing.setCourseType(updated.getCourseType());
        existing.setSelectedTechnology(updated.getSelectedTechnology());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());

        return enrollmentRepository.save(existing);
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
}
