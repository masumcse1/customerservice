package com.opt.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CustomerDto {
    private Long id;
    private String customerId;
    private String customerName;
    private String description;
    private BigDecimal balance;
    private LocalDate transactionDate;
    private LocalTime transactionTime;
    private Boolean status;
    private String phoneNumber;
}
