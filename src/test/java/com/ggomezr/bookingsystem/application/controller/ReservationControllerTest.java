package com.ggomezr.bookingsystem.application.controller;

import com.ggomezr.bookingsystem.application.service.ReservationService;
import com.ggomezr.bookingsystem.domain.dto.ReservationDto;
import com.ggomezr.bookingsystem.domain.entity.Reservation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReservationControllerTest {
    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    public void testGetAllReservations() {
        List<Reservation> expectedReservations = List.of(
                new Reservation(), new Reservation());

        when(reservationService.getAllReservations())
                .thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationController.getAllReservation();

        assertEquals(expectedReservations, actualReservations);
        verify(reservationService).getAllReservations();
    }

    @Test
    public void testGetReservationById() throws Exception {
        Reservation expectedReservation = new Reservation();
        when(reservationService.getReservationById(1))
                .thenReturn(Optional.of(expectedReservation));

        Optional<Reservation> actualReservation = reservationController.getReservationById(1);

        assertTrue(actualReservation.isPresent());
        assertEquals(expectedReservation, actualReservation.get());
    }

    @Test
    public void testGetReservationByUserId() {
        Integer userId = 1;
        List<Reservation> expectedReservations = List.of(new Reservation());

        when(reservationService.getReservationsByUserId(userId))
                .thenReturn(expectedReservations);

        List<Reservation> actualReservations =
                reservationController.getReservationByUserId(userId);

        assertEquals(expectedReservations, actualReservations);
        verify(reservationService).getReservationsByUserId(userId);
    }

    @Test
    public void testGetReservationsByRoomId() {
        Integer roomId = 1;
        List<Reservation> expectedReservations = List.of(new Reservation());

        when(reservationService.getReservationsByRoomId(roomId))
                .thenReturn(expectedReservations);

        List<Reservation> actualReservations =
                reservationController.getReservationsByRoomId(roomId);

        assertEquals(expectedReservations, actualReservations);
        verify(reservationService).getReservationsByRoomId(roomId);
    }

    @Test
    public void testGetReservationsByDates() {
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-01-31");

        List<Reservation> expectedReservations = List.of(new Reservation());

        when(reservationService.getReservationsByDates(startDate, endDate))
                .thenReturn(expectedReservations);

        List<Reservation> actualReservations =
                reservationController.getReservationsByDates(startDate, endDate);

        assertEquals(expectedReservations, actualReservations);
        verify(reservationService).getReservationsByDates(startDate, endDate);
    }

    @Test
    public void testGetReservationsByUserIdAndDates() {
        Integer userId = 1;
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-01-31");

        List<Reservation> expectedReservations = List.of(new Reservation());

        when(reservationService.getReservationsByUserIdAndDates(userId, startDate, endDate))
                .thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationController
                .getReservationsByUserIdAndDates(userId, startDate, endDate);

        assertEquals(expectedReservations, actualReservations);
        verify(reservationService)
                .getReservationsByUserIdAndDates(userId, startDate, endDate);
    }

    @Test
    public void testCreateReservation()
            throws Exception {

        ReservationDto reservationDto = new ReservationDto(1, 1, 1,
                LocalDate.now(), LocalDate.now().plusDays(3),
                new BigDecimal(50));

        reservationController.createReservation(reservationDto);

        verify(reservationService).createReservation(reservationDto);
    }

    @Test
    public void testUpdateReservation()
            throws Exception {

        Integer reservationId = 1;
        ReservationDto reservationDto = new ReservationDto(1, 1, 1,
                LocalDate.now(), LocalDate.now().plusDays(3),
                new BigDecimal(50));

        Reservation existingReservation = new Reservation();
        when(reservationService.getReservationById(reservationId))
                .thenReturn(Optional.of(existingReservation));

        reservationController.updateReservation(reservationId, reservationDto);

        verify(reservationService).updateReservation(reservationId, reservationDto);
    }

    @Test
    public void testGetTotalReservationPriceForUser() {

        int userId = 1;
        BigDecimal expectedTotal = new BigDecimal(100);

        when(reservationService.getTotalReservationPriceForUser(userId))
                .thenReturn(expectedTotal);

        BigDecimal actualTotal = reservationController.getTotalReservationPriceForUser(userId);

        assertEquals(expectedTotal, actualTotal);
        verify(reservationService).getTotalReservationPriceForUser(userId);
    }
}
