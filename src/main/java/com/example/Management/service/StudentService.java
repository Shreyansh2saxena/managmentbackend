package com.example.Management.service;

import com.example.Management.dto.StudentFullInfoDTO;
import com.example.Management.entity.CourseEnrollment;
import com.example.Management.entity.Payment;
import com.example.Management.entity.Student;
import com.example.Management.enums.PaymentStatus;
import com.example.Management.repository.CourseEnrollmentRepository;
import com.example.Management.repository.PaymentRepository;
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
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final PaymentRepository paymentRepository;

    public Student saveStudent(Student student) {
        if (student.getRegistrationDate() == null) {
            student.setRegistrationDate(LocalDate.now());
        }

        // Assign ID if it's a new student
        if (student.getId() == null || student.getId().isBlank()) {
            student.setId(generateStudentId());
        }

        return studentRepository.save(student);
    }

    private String generateStudentId() {
        String prefix = "STU";
        String datePart = LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMM"));
        List<Student> students = studentRepository.findAll();

        int maxNumber = students.stream()
                .filter(s -> s.getId().startsWith(prefix + datePart))
                .mapToInt(s -> Integer.parseInt(s.getId().substring(9)))  // STU + yyyyMM = 9 chars
                .max()
                .orElse(0);

        int newNumber = maxNumber + 1;
        String numberPart = String.format("%03d", newNumber);  // Always 3 digits
        return prefix + datePart + numberPart;
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

    public StudentFullInfoDTO getStudentFullInfo(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<CourseEnrollment> enrollments = courseEnrollmentRepository.findByStudentId(studentId);
        List<Payment> payments = paymentRepository.findAll().stream()
                .filter(p -> p.getEnrollment().getStudent().getId().equals(studentId))
                .toList();

        double totalPaid = payments.stream().mapToDouble(Payment::getAmountPaid).sum();
        double totalDue = payments.stream().mapToDouble(Payment::getAmountDue).sum() - totalPaid;

        int totalInstallments = payments.size();
        int paidInstallments = (int) payments.stream().filter(p -> p.getStatus() == PaymentStatus.PAID).count();
        int pendingInstallments = (int) payments.stream().filter(p -> p.getStatus() == PaymentStatus.PENDING).count();

        StudentFullInfoDTO dto = new StudentFullInfoDTO();
        dto.setStudentId(student.getId());
        dto.setFullName(student.getFullName());
        dto.setEmail(student.getEmail());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setEnrollments(enrollments);
        dto.setPayments(payments);
        dto.setTotalPaid(totalPaid);
        dto.setTotalDue(totalDue);
        dto.setTotalInstallments(totalInstallments);
        dto.setPaidInstallments(paidInstallments);
        dto.setPendingInstallments(pendingInstallments);

        return dto;
    }
}

