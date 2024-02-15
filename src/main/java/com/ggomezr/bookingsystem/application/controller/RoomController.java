package com.ggomezr.bookingsystem.application.controller;

import com.ggomezr.bookingsystem.application.service.RoomService;
import com.ggomezr.bookingsystem.domain.dto.RoomDto;
import com.ggomezr.bookingsystem.domain.entity.Room;
import com.ggomezr.bookingsystem.domain.exceptions.RoomNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Get all rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rooms found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Room.class))
                    }),
            @ApiResponse(responseCode = "403", description = "No rooms found", content = @Content)
    })
    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @Operation(summary = "Get room by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Room.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Room not found", content = @Content)
    })
    @GetMapping("/rooms/{id}")
    public Optional<Room> getRoomById(@Parameter(description = "Room id", example = "1")@PathVariable Integer id) throws RoomNotFoundException {
        return roomService.getRoomById(id);
    }

    @Operation(summary = "Create room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Room.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Room could not be created", content = @Content)
    })
    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void createRoom(@RequestBody RoomDto roomDto) {
        roomService.createRoom(roomDto);
    }

    @Operation(summary = "Update room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Room.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Room could not be updated", content = @Content)
    })
    @PutMapping("/rooms/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateRoom(@Parameter(description = "Room id", example = "1")@PathVariable Integer id, @RequestBody RoomDto roomDto) throws RoomNotFoundException {
        roomService.updateRoom(id, roomDto);
    }

    @Operation(summary = "Delete room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Room.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Room could not be deleted", content = @Content)
    })
    @DeleteMapping("/rooms/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRoom(@Parameter(description = "Room id", example = "1")@PathVariable Integer id) {
        roomService.deleteRoom(id);
    }
}
