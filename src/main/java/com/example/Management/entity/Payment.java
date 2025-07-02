package com.example.Management.entity;

import com.example.Management.enums.PaymentStatus;
import com.example.Management.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CourseEnrollment enrollment;

    private int installmentNumber;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private double amountDue;
    private double amountPaid;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDate dueDate;
    private LocalDate paymentDate;
    private String paymentMode;
    private String transactionId;
    private String comment;

}
