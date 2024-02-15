package com.ggomezr.bookingsystem.application.controller;

import com.ggomezr.bookingsystem.application.lasting.ERole;
import com.ggomezr.bookingsystem.application.service.UserService;
import com.ggomezr.bookingsystem.domain.dto.UserDto;
import com.ggomezr.bookingsystem.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetAllUsers() {
        List<User> expectedUsers = List.of(new User(), new User());
        when(userService.getAllUsers()).thenReturn(expectedUsers);

        List<User> actualUsers = userController.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
        verify(userService).getAllUsers();
    }

    @Test
    public void testGetUserById() throws Exception {
        User expectedUser = new User(1, "John", "Doe", "email@email.com","123465789", "password", true, ERole.USER);
        when(userService.getUserById(1)).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userController.getUserById(1);

        assertTrue(actualUser.isPresent());
        assertEquals(expectedUser, actualUser.get());
    }

    @Test
    public void testUpdateUser() throws Exception {
        Integer id = 1;
        UserDto updatedUserDto = new UserDto(1, "Jane", "Doe", "email@email.com","123465789", "newPass", true, ERole.USER);

        User existingUser = new User(1, "John", "Doe", "email@email.com", "123456798", "oldPass", true, ERole.USER);
        when(userService.getUserById(id)).thenReturn(Optional.of(existingUser));

        userController.updateUser(id, updatedUserDto);

        verify(userService).updateUser(eq(id), eq(updatedUserDto));
    }

    @Test
    public void testDeleteUser(){
        userController.deleteUser(1);

        verify(userService).deleteUser(1);
    }
}
