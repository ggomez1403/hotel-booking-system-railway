package com.ggomezr.bookingsystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BillDetailDto(
        Integer id,
        Integer reservationId,
        String description,
        BigDecimal reservationAmount
) {
}
