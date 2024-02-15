package com.ggomezr.bookingsystem.application.controller;

import com.ggomezr.bookingsystem.application.lasting.ERole;
import com.ggomezr.bookingsystem.application.service.AuthenticationService;
import com.ggomezr.bookingsystem.domain.dto.AuthenticationDto;
import com.ggomezr.bookingsystem.domain.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationControllerTest {
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    public void testRegister() {
        UserDto userDto = new UserDto(1,"Juan", "Perez", "jperez@email.com", "123456789", "password123", true, ERole.USER);
        String token = "token-123";

        when(authenticationService.register(userDto)).thenReturn(token);

        ResponseEntity<?> response = authenticationController.register(userDto);

        assertEquals(token, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(authenticationService).register(userDto);
    }

    @Test
    public void testAuthenticate() {
        AuthenticationDto authDto = new AuthenticationDto("jperez@email.com", "password123");
        String token = "token-456";

        when(authenticationService.authenticate(authDto)).thenReturn(token);

        ResponseEntity<?> response = authenticationController.authenticate(authDto);

        assertEquals(token, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authenticationService).authenticate(authDto);
    }
}
