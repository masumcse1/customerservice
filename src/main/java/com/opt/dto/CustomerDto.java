package com.opt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
@Schema(
        description = "external CustomerDto Model Information"
)
@Data
public class CustomerDto {
    @Schema(
            description = "inernal CustomerDto Model Information"
    )
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
