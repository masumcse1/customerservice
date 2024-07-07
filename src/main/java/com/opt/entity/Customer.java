package com.opt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
public class Customer {

    @Id
    private Long id;
    private String customerId;
    private String customerName;
    private String description;
    private BigDecimal balance;
    private LocalDate admissionDate;
    private LocalTime admissionTime;
    private Boolean status;
    @NotBlank
    @Column(name = "phone_number",nullable = false, unique = true)
    private String phoneNumber;

    public Customer(Long id, String customerName, String phoneNumber) {
        this.id = id;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
