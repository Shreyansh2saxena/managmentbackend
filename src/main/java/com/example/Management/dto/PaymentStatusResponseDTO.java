package com.example.Management.dto;


import lombok.Data;

@Data
public class PaymentStatusResponseDTO {
    private double totalPaid;
    private double totalDue;
    private int totalInstallments;
    private int paidInstallments;
    private int pendingInstallments;
}
