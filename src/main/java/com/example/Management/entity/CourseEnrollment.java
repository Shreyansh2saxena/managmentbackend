package com.example.Management.entity;

import com.example.Management.enums.CourseType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    private String selectedTechnology;
    private LocalDate startDate;
    private LocalDate endDate;
}