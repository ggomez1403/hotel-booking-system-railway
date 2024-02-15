package com.ggomezr.bookingsystem.application.controller;

import com.ggomezr.bookingsystem.application.service.UserService;
import com.ggomezr.bookingsystem.domain.dto.UserDto;
import com.ggomezr.bookingsystem.domain.entity.User;
import com.ggomezr.bookingsystem.domain.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController{

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(responseCode = "403", description = "No users found", content = @Content)
    })
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(responseCode = "403", description = "User not found", content = @Content)
    })
    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@Parameter(description = "User id", example = "1") @PathVariable Integer id) throws UserNotFoundException {
        return userService.
                getUserById(id);
    }

    @Operation(summary = "Update user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(responseCode = "403", description = "User could not be updated", content = @Content)
    })
    @PutMapping("/users/{id}")
    public void updateUser(@Parameter(description = "User id", example = "1") @PathVariable Integer id, @RequestBody UserDto userDto) throws UserNotFoundException {
        userService.updateUser(id, userDto);
    }

    @Operation(summary = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(responseCode = "403", description = "User could not be deleted", content = @Content),
    })
    @DeleteMapping("/users/{id}")
    public void deleteUser(@Parameter(description = "User id", example = "1") @PathVariable Integer id){
        userService.deleteUser(id);
    }
}
