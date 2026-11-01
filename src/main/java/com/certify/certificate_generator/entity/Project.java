package com.certify.certificate_generator.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Project")

public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    String name;
    @Column
    String description;
    @Column
    LocalDateTime createAt;
    @Column
    LocalDateTime updatedAt;
}
