package com.example.Management.repository;

import com.example.Management.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findByEnrollmentId(Long enrollmentId, Pageable pageable);
    List<Payment> findByEnrollmentId(Long enrollmentId);
}