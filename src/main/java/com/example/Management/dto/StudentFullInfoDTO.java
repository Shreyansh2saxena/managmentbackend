package com.example.Management.dto;


import com.example.Management.entity.CourseEnrollment;
import com.example.Management.entity.Payment;
import lombok.Data;

import java.util.List;

@Data
public class StudentFullInfoDTO {
    private String studentId;
    private String fullName;
    private String email;
    private String phoneNumber;

    private List<CourseEnrollment> enrollments;
    private List<Payment> payments;

    private double totalPaid;
    private double totalDue;
    private int totalInstallments;
    private int paidInstallments;
    private int pendingInstallments;
}

