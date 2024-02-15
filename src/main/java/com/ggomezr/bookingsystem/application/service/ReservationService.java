package com.ggomezr.bookingsystem.application.service;

import com.ggomezr.bookingsystem.domain.dto.ReservationDto;
import com.ggomezr.bookingsystem.domain.entity.Reservation;
import com.ggomezr.bookingsystem.domain.entity.Room;
import com.ggomezr.bookingsystem.domain.exceptions.RoomNotAvailableException;
import com.ggomezr.bookingsystem.domain.exceptions.RoomNotFoundException;
import com.ggomezr.bookingsystem.domain.exceptions.UserNotFoundException;
import com.ggomezr.bookingsystem.domain.exceptions.ReservationNotFoundException;
import com.ggomezr.bookingsystem.domain.repository.ReservationRepository;
import com.ggomezr.bookingsystem.domain.repository.RoomRepository;
import com.ggomezr.bookingsystem.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService{

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Integer id) throws ReservationNotFoundException {
        return Optional.ofNullable(reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new));
    }

    public List<Reservation> getReservationsByUserId(Integer userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getReservationsByRoomId(Integer roomId) {
        return reservationRepository.findByRoomId(roomId);
    }

    public List<Reservation> getReservationsByDates(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findByStartDateBetween(startDate, endDate);
    }

    public List<Reservation> getReservationsByUserIdAndDates(Integer userId, LocalDate startDate, LocalDate endDate){
        return reservationRepository.findReservationsByUserIdAndDates(userId, startDate, endDate);
    }

    public BigDecimal getTotalReservationPriceForUser(Integer userId) {

        return reservationRepository.findByUserId(userId).stream()
                .map(Reservation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public void createReservation(ReservationDto reservationDto) throws UserNotFoundException, RoomNotFoundException, RoomNotAvailableException {
        Room room = roomRepository.findById(reservationDto.roomId()).orElseThrow(RoomNotFoundException::new);

        if(!room.getAvailable()){
            throw new RoomNotAvailableException();
        }

        Reservation reservation = Reservation.builder()
                .user(userRepository.findById(reservationDto.userId()).orElseThrow(UserNotFoundException::new))
                .room(roomRepository.findById(reservationDto.roomId()).orElseThrow(RoomNotFoundException::new))
                .startDate(reservationDto.startDate())
                .endDate(reservationDto.endDate())
                .amount(roomRepository.findById(reservationDto.roomId()).orElseThrow(RoomNotFoundException::new).getTotalPrice().multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(reservationDto.startDate(), reservationDto.endDate()))))
                .build();
        room.setAvailable(false);
        reservationRepository.save(reservation);

    }

    public void updateReservation(Integer id, ReservationDto reservationDto) throws ReservationNotFoundException, UserNotFoundException, RoomNotFoundException {
        Reservation existingReservation = reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);

        existingReservation.setUser(userRepository.findById(reservationDto.userId()).orElseThrow(UserNotFoundException::new));
        existingReservation.setRoom(roomRepository.findById(reservationDto.roomId()).orElseThrow(RoomNotFoundException::new));
        existingReservation.setStartDate(reservationDto.startDate());
        existingReservation.setEndDate(reservationDto.endDate());

        reservationRepository.save(existingReservation);
    }

    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }
}
