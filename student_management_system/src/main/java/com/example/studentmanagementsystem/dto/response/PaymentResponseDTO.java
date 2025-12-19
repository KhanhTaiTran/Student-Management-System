package com.example.studentmanagementsystem.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private BigDecimal totalTuition;
    private BigDecimal totalPaid;
    private BigDecimal balance; // (total - paid)
}
