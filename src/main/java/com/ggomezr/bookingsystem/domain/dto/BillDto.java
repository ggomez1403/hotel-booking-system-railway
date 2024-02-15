package com.ggomezr.bookingsystem.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BillDto(
        Integer id,
        Integer billDetailId,
        BigDecimal totalAmount,
        LocalDate issuedDate
) {
}
