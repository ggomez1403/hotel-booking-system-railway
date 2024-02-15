package com.ggomezr.bookingsystem.application.service;

import com.ggomezr.bookingsystem.domain.dto.ReservationDto;
import com.ggomezr.bookingsystem.domain.entity.Reservation;
import com.ggomezr.bookingsystem.domain.entity.Room;
import com.ggomezr.bookingsystem.domain.entity.User;
import com.ggomezr.bookingsystem.domain.exceptions.ReservationNotFoundException;
import com.ggomezr.bookingsystem.domain.exceptions.RoomNotAvailableException;
import com.ggomezr.bookingsystem.domain.exceptions.RoomNotFoundException;
import com.ggomezr.bookingsystem.domain.exceptions.UserNotFoundException;
import com.ggomezr.bookingsystem.domain.repository.ReservationRepository;
import com.ggomezr.bookingsystem.domain.repository.RoomRepository;
import com.ggomezr.bookingsystem.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void testGetAllReservation() {
        List<Reservation> mockReservations = Arrays.asList(new Reservation(), new Reservation());

        when(reservationRepository.findAll()).thenReturn(mockReservations);

        List<Reservation> reservations = reservationService.getAllReservations();

        assertEquals(mockReservations, reservations);
        verify(reservationRepository).findAll();
    }

    @Test
    public void testGetReservationById() throws ReservationNotFoundException {
        Integer id = 1;
        Reservation mockReservation = new Reservation();

        when(reservationRepository.findById(id)).thenReturn(Optional.of(mockReservation));

        Optional<Reservation> optionalReservation = reservationService.getReservationById(id);

        assertTrue(optionalReservation.isPresent());
        assertSame(mockReservation, optionalReservation.get());
        verify(reservationRepository).findById(id);
    }

    @Test
    public void testGetReservationsByUserId() {
        User user = User.builder().id(1).build();
        Reservation reservation = Reservation.builder()
                .user(user)
                .build();

        when(reservationRepository.findByUserId(1))
                .thenReturn(List.of(reservation));

        List<Reservation> result = reservationService.getReservationsByUserId(1);

        assertEquals(1, result.size());
        assertEquals(user, result.get(0).getUser());
    }

    @Test
    public void testGetReservationsByRoomId() {
        Room room = Room.builder().id(1).build();
        Reservation reservation = Reservation.builder()
                .room(room)
                .build();

        when(reservationRepository.findByRoomId(1))
                .thenReturn(List.of(reservation));

        List<Reservation> result = reservationService.getReservationsByRoomId(1);

        assertEquals(1, result.size());
        assertEquals(room, result.get(0).getRoom());
    }

    @Test
    public void testGetReservationsByDates() {
        LocalDate start = LocalDate.of(2023,1,1);
        LocalDate end = LocalDate.of(2023,1,31);

        Reservation reservation = Reservation.builder()
                .startDate(LocalDate.of(2023, 1, 15))
                .build();

        when(reservationRepository.findByStartDateBetween(start, end))
                .thenReturn(List.of(reservation));

        List<Reservation> result = reservationService
                .getReservationsByDates(start, end);

        assertEquals(1, result.size());
        assertTrue(start.isBefore(result.get(0).getStartDate())
                && end.isAfter(result.get(0).getStartDate()));
    }

    @Test
    public void testGetReservationsByUserIdAndDates() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(5);

        User user = User.builder().id(1).build();
        Reservation reservation = Reservation.builder()
                .user(user)
                .startDate(start.plusDays(1))
                .build();

        when(reservationRepository.findReservationsByUserIdAndDates(1, start, end))
                .thenReturn(List.of(reservation));

        List<Reservation> result = reservationService
                .getReservationsByUserIdAndDates(1, start, end);

        assertEquals(1, result.size());
        assertEquals(user, result.get(0).getUser());
        assertTrue(start.isBefore(result.get(0).getStartDate())
                && end.isAfter(result.get(0).getStartDate()));
    }

    @Test
    public void testCreateReservation() throws UserNotFoundException, RoomNotFoundException, RoomNotAvailableException {
        Room room = Room.builder()
                .id(1)
                .available(true)
                .initialPrice(BigDecimal.TEN)
                .taxesAndFees(BigDecimal.ONE)
                .totalPrice(BigDecimal.TEN.add(BigDecimal.ONE))
                .build();

        User user = User.builder().id(1).build();

        ReservationDto reservationDto = new ReservationDto(null, 1, 1,
                LocalDate.now(), LocalDate.now().plusDays(5), null);

        when(roomRepository.findById(1)).thenReturn(Optional.of(room));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        reservationService.createReservation(reservationDto);

        BigDecimal expectedAmount = room.getTotalPrice().multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(reservationDto.startDate(), reservationDto.endDate())));

        verify(reservationRepository).save(argThat(r -> {
            assertEquals(user, r.getUser());
            assertEquals(room, r.getRoom());
            assertEquals(expectedAmount, r.getAmount());
            return true;
        }));
    }

    @Test
    public void testUpdateReservation_success() throws Exception {
        Integer id = 1;
        Reservation existingReservation = Reservation.builder().id(id).build();

        User user = User.builder().id(1).build();
        Room room = Room.builder().id(1).build();

        ReservationDto reservationDto = new ReservationDto(null, 1, 1,
                LocalDate.now(), LocalDate.now().plusDays(5), null);

        when(reservationRepository.findById(id))
                .thenReturn(Optional.of(existingReservation));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(roomRepository.findById(1)).thenReturn(Optional.of(room));

        reservationService.updateReservation(id, reservationDto);

        verify(reservationRepository).save(argThat(r -> {
            assertEquals(id, r.getId());
            assertEquals(user, r.getUser());
            assertEquals(room, r.getRoom());
            assertEquals(reservationDto.startDate(), r.getStartDate());
            assertEquals(reservationDto.endDate(), r.getEndDate());
            return true;
        }));
    }

    @Test
    public void testDeleteReservation() {
        Integer id = 1;

        reservationService.deleteReservation(id);

        verify(reservationRepository).deleteById(id);
    }
}
