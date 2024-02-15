package com.ggomezr.bookingsystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ggomezr.bookingsystem.application.lasting.ERole;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        Integer id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String password,
        Boolean enable,
        ERole role
) {
}
