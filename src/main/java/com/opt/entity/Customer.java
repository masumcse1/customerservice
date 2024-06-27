package com.opt.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private String customerName;
    private String description;
    private BigDecimal balance;
    private LocalDate admissionDate;
    private LocalTime admissionTime;
    private Boolean status;

}
