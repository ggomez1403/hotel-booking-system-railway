package com.ggomezr.bookingsystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoomDto(
        Integer id,
        String name,
        String imgUrl,
        Boolean available,
        String type,
        Integer capacity,
        BigDecimal initialPrice,
        BigDecimal taxesAndFees,
        BigDecimal totalPrice,
        List<String> bathroomAmenities,
        List<String> bedroomAmenities,
        List<String> entertainmentAmenities,
        List<String> foodAndDrinksAmenities,
        List<String> internetAmenities,
        List<String> moreAmenities,
        List<String> highlights
) {
}
