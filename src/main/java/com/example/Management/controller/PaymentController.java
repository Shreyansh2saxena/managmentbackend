package com.example.Management.controller;

import com.example.Management.dto.PaymentRequestDTO;
import com.example.Management.dto.PaymentStatusResponseDTO;
import com.example.Management.entity.Payment;
import com.example.Management.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Payment createPayment(@RequestBody PaymentRequestDTO dto) {
        return paymentService.createPayment(dto);
    }

    @PutMapping("/{id}")
    public Payment updatePayment(@PathVariable Long id, @RequestBody PaymentRequestDTO dto) {
        return paymentService.updatePayment(id, dto);
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public Page<Payment> getPaymentsByEnrollment(@PathVariable Long enrollmentId, Pageable pageable) {
        return paymentService.getPaymentsByEnrollment(enrollmentId, pageable);
    }

    @GetMapping("/status/{enrollmentId}")
    public PaymentStatusResponseDTO getStatus(@PathVariable Long enrollmentId) {
        return paymentService.getPaymentStatus(enrollmentId);
    }
}