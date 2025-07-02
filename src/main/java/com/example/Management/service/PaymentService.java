package com.example.Management.service;

import com.example.Management.dto.PaymentRequestDTO;
import com.example.Management.dto.PaymentStatusResponseDTO;
import com.example.Management.entity.CourseEnrollment;
import com.example.Management.entity.Payment;
import com.example.Management.enums.PaymentStatus;
import com.example.Management.repository.CourseEnrollmentRepository;
import com.example.Management.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CourseEnrollmentRepository enrollmentRepository;

    public Payment createPayment(PaymentRequestDTO dto) {
        CourseEnrollment enrollment = enrollmentRepository.findById(dto.getEnrollmentId())
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Payment payment = Payment.builder()
                .enrollment(enrollment)
                .installmentNumber(dto.getInstallmentNumber())
                .paymentType(dto.getPaymentType())
                .amountDue(dto.getAmountDue())
                .amountPaid(dto.getAmountPaid())
                .status(dto.getStatus())
                .dueDate(dto.getDueDate())
                .paymentDate(dto.getPaymentDate())
                .paymentMode(dto.getPaymentMode())
                .transactionId(dto.getTransactionId())
                .comment(dto.getComment())
                .build();

        return paymentRepository.save(payment);
    }

    public Page<Payment> getPaymentsByEnrollment(Long enrollmentId, Pageable pageable) {
        return paymentRepository.findByEnrollmentId(enrollmentId, pageable);
    }

    public PaymentStatusResponseDTO getPaymentStatus(Long enrollmentId) {
        var list = paymentRepository.findByEnrollmentId(enrollmentId);
        PaymentStatusResponseDTO response = new PaymentStatusResponseDTO();

        response.setTotalInstallments(list.size());
        response.setPaidInstallments((int) list.stream().filter(p -> p.getStatus() == PaymentStatus.PAID).count());
        response.setPendingInstallments((int) list.stream().filter(p -> p.getStatus() == PaymentStatus.PENDING).count());

        double totalPaid = list.stream().mapToDouble(Payment::getAmountPaid).sum();
        double totalDue = list.stream().mapToDouble(Payment::getAmountDue).sum() - totalPaid;

        response.setTotalPaid(totalPaid);
        response.setTotalDue(totalDue);
        return response;
    }

    public Payment updatePayment(Long id, PaymentRequestDTO dto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setAmountPaid(dto.getAmountPaid());
        payment.setStatus(dto.getStatus());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setPaymentMode(dto.getPaymentMode());
        payment.setTransactionId(dto.getTransactionId());
        payment.setComment(dto.getComment());

        return paymentRepository.save(payment);
    }
}
