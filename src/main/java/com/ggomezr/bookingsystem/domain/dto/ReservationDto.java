package com.ggomezr.bookingsystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReservationDto(
        Integer id,
        Integer userId,
        Integer roomId,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal amount
) {
}
