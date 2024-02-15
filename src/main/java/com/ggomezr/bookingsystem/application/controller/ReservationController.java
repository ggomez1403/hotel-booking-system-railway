package com.ggomezr.bookingsystem.application.controller;

import com.ggomezr.bookingsystem.application.service.ReservationService;
import com.ggomezr.bookingsystem.domain.dto.ReservationDto;
import com.ggomezr.bookingsystem.domain.entity.Reservation;
import com.ggomezr.bookingsystem.domain.exceptions.RoomNotAvailableException;
import com.ggomezr.bookingsystem.domain.exceptions.RoomNotFoundException;
import com.ggomezr.bookingsystem.domain.exceptions.UserNotFoundException;
import com.ggomezr.bookingsystem.domain.exceptions.ReservationNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Get all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Reservation.class))
                    }),
            @ApiResponse(responseCode = "403", description = "No reservations found", content = @Content)
    })
    @GetMapping("/reservations")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Reservation> getAllReservation(){
        return reservationService.getAllReservations();
    }

    @Operation(summary = "Get reservation by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Reservation.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Reservation not found", content = @Content)
    })
    @GetMapping("/reservations/{id}")
    public Optional<Reservation> getReservationById(@Parameter(description = "Reservation id", example = "1")@PathVariable Integer id) throws ReservationNotFoundException{
        return reservationService.getReservationById(id);
    }

    @Operation(summary = "Get reservations by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Reservation.class))
                    }),
            @ApiResponse(responseCode = "403", description = "No reservations found", content = @Content)
    })
    @GetMapping
    public List<Reservation> getReservationByUserId(@Parameter(description = "User id", example = "?userId=1")@RequestParam Integer userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @Operation(summary = "Get reservations by room id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Reservation.class))
                    }),
            @ApiResponse(responseCode = "403", description = "No reservations found", content = @Content)
    })
    @GetMapping("/rooms/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Reservation> getReservationsByRoomId(@Parameter(description = "Room id", example = "1")@PathVariable Integer roomId){
        return reservationService.getReservationsByRoomId(roomId);
    }

    @Operation(summary = "Get reservations by reservation start and end dates ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Reservation.class))
                    }),
            @ApiResponse(responseCode = "403", description = "No reservations found", content = @Content)
    })
    @GetMapping("/dates/{startDate}/{endDate}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Reservation> getReservationsByDates(@Parameter(description = "Start and end reservation dates", example = "/2023-12-20/2023-12-31")@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return reservationService.getReservationsByDates(startDate, endDate);
    }

    @Operation(summary = "Get reservations by user id and reservation start and end dates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Reservation.class))
                    }),
            @ApiResponse(responseCode = "403", description = "No reservations found", content = @Content)
    })
    @GetMapping("/dates/{userId}/{startDate}/{endDate}")
    public List<Reservation> getReservationsByUserIdAndDates(@Parameter(description = "User id, start and end dates", example = "/1/2023-12-20/2023-12-31")@PathVariable Integer userId, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate){
        return reservationService.getReservationsByUserIdAndDates(userId, startDate, endDate);
    }

    @Operation(summary = "Get total reservation price for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation price found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Reservation.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Reservation not found", content = @Content)
    })
    @GetMapping("/reservations/total/{userId}")
    public BigDecimal getTotalReservationPriceForUser(@Parameter(description = "User id", example = "1")@PathVariable Integer userId) {
        return reservationService.getTotalReservationPriceForUser(userId);
    }

    @Operation(summary = "Create reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Reservation.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Reservation could not be created", content = @Content)
    })
    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReservation(@RequestBody ReservationDto reservationDto) throws UserNotFoundException, RoomNotFoundException, RoomNotAvailableException {
        reservationService.createReservation(reservationDto);
    }

    @Operation(summary = "Update reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Reservation.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Reservation could not be updated", content = @Content)
    })
    @PutMapping("/reservations/{id}")
    public void updateReservation(@Parameter(description = "Reservation id", example = "1")@PathVariable Integer id, @RequestBody ReservationDto reservationDto) throws ReservationNotFoundException, UserNotFoundException, RoomNotFoundException {
        reservationService.updateReservation(id, reservationDto);
    }

    @Operation(summary = "Delete reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Reservation.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Reservation could not be deleted", content = @Content)
    })
    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@Parameter(description = "Reservation id", example = "1")@PathVariable Integer id){
        reservationService.deleteReservation(id);
    }
}
