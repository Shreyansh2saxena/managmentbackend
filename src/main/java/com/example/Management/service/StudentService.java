package com.example.Management.service;

import com.example.Management.entity.Student;
import com.example.Management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student saveStudent(Student student) {
        if (student.getRegistrationDate() == null) {
            student.setRegistrationDate(LocalDate.now());
        }
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setFullName(updatedStudent.getFullName());
            student.setEmail(updatedStudent.getEmail());
            student.setPhoneNumber(updatedStudent.getPhoneNumber());
            student.setRegistrationDate(updatedStudent.getRegistrationDate());
            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student saveOrUpdateStudentByEmail(Student student) {
        Optional<Student> existing = studentRepository.findByEmail(student.getEmail());
        if (existing.isPresent()) {
            Student s = existing.get();
            s.setFullName(student.getFullName());
            s.setPhoneNumber(student.getPhoneNumber());
            s.setRegistrationDate(student.getRegistrationDate());
            return studentRepository.save(s);
        } else {
            if (student.getRegistrationDate() == null) {
                student.setRegistrationDate(LocalDate.now());
            }
            return studentRepository.save(student);
        }
    }
}

