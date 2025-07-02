package com.example.Management.dto;


import com.example.Management.enums.PaymentStatus;
import com.example.Management.enums.PaymentType;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PaymentRequestDTO {
    private Long enrollmentId;
    private int installmentNumber;
    private PaymentType paymentType;
    private double amountDue;
    private double amountPaid;
    private PaymentStatus status;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private String paymentMode;
    private String transactionId;
    private String comment;
}