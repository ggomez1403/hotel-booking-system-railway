package com.ggomezr.bookingsystem.application.service;

import com.ggomezr.bookingsystem.domain.dto.UserDto;
import com.ggomezr.bookingsystem.domain.entity.User;
import com.ggomezr.bookingsystem.domain.exceptions.UserNotFoundException;
import com.ggomezr.bookingsystem.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetAllUsers() {
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> users = userService.getAllUsers();

        assertEquals(mockUsers, users);
        verify(userRepository).findAll();
    }

    @Test
    public void testGetUserById() throws UserNotFoundException {
        Integer id = 1;
        User mockUser = new User();

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        Optional<User> optionalUser = userService.getUserById(id);

        assertTrue(optionalUser.isPresent());
        assertSame(mockUser, optionalUser.get());
        verify(userRepository).findById(id);
    }

    @Test
    public void testUpdateUser() throws UserNotFoundException {
        Integer id = 1;
        User existingUser = new User();
        UserDto userDto = new UserDto(null, "New First", "New Last", null, "123456789", "newPass", null, null);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedPass");

        userService.updateUser(id, userDto);

        assertEquals("New First", existingUser.getFirstName());
        assertEquals("New Last", existingUser.getLastName());
        assertEquals("encodedPass", existingUser.getPassword());
        verify(userRepository).findById(id);
        verify(userRepository).save(existingUser);
    }

    @Test
    public void testDeleteUser() {
        Integer id = 1;

        userService.deleteUser(id);

        verify(userRepository).deleteById(id);
    }
}
