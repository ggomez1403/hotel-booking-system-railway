package com.ggomezr.bookingsystem.domain.dto;

public record AuthenticationDto(
        String email,
        String password
) {
}
