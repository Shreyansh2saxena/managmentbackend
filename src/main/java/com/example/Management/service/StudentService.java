package com.example.Management.service;

import com.example.Management.entity.Student;
import com.example.Management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return repository.findById(id);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        return repository.findById(id).map(student -> {

            if (updatedStudent.getStudentName() != null)
                student.setStudentName(updatedStudent.getStudentName());

            if (updatedStudent.getAddress() != null)
                student.setAddress(updatedStudent.getAddress());

            if (updatedStudent.getPhone() != null)
                student.setPhone(updatedStudent.getPhone());

            if (updatedStudent.getRole() != null)
                student.setRole(updatedStudent.getRole());

            if (updatedStudent.getEmail() != null)
                student.setEmail(updatedStudent.getEmail());

            if (updatedStudent.getCourse() != null)
                student.setCourse(updatedStudent.getCourse());

            if (updatedStudent.getAmount() != null)
                student.setAmount(updatedStudent.getAmount());

            return repository.save(student);

        }).orElse(null);
    }


    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }

    public Student saveOrUpdateStudentByEmail(Student student) {
        Optional<Student> existing = repository.findByEmail(student.getEmail());

        if (existing.isPresent()) {
            Student existingStudent = existing.get();
            existingStudent.setCourse(student.getCourse());
            existingStudent.setAmount(student.getAmount());
            return repository.save(existingStudent);
        } else {
            return repository.save(student);
        }
    }

}
