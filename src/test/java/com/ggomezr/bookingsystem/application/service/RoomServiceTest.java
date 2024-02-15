package com.ggomezr.bookingsystem.application.service;

import com.ggomezr.bookingsystem.domain.dto.RoomDto;
import com.ggomezr.bookingsystem.domain.entity.Room;
import com.ggomezr.bookingsystem.domain.exceptions.RoomNotFoundException;
import com.ggomezr.bookingsystem.domain.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    public void testGetAllRooms() {
        List<Room> mockedRooms = new ArrayList<>();
        mockedRooms.add(new Room(1,
                "Ejemplo Room",
                "https://ejemplo.com/imagen.jpg",
                true,
                "Suite",
                2,
                new BigDecimal("150.00"),
                new BigDecimal("20.00"),
                new BigDecimal("170.00"),
                Arrays.asList("Toallas", "Champú", "Jabón"),
                Arrays.asList("Cama King Size", "Armario"),
                Arrays.asList("TV", "Minibar"),
                Arrays.asList("Desayuno incluido"),
                Arrays.asList("Wi-Fi gratuito"),
                Arrays.asList("Vistas panorámicas"),
                Arrays.asList("Piscina, Gimnasio")));

        mockedRooms.add(new Room(2,
                "Ejemplo Room",
                "https://ejemplo.com/imagen.jpg",
                true,
                "Suite",
                2,
                new BigDecimal("150.00"),
                new BigDecimal("20.00"),
                new BigDecimal("170.00"),
                Arrays.asList("Toallas", "Champú", "Jabón"),
                Arrays.asList("Cama King Size", "Armario"),
                Arrays.asList("TV", "Minibar"),
                Arrays.asList("Desayuno incluido"),
                Arrays.asList("Wi-Fi gratuito"),
                Arrays.asList("Vistas panorámicas"),
                Arrays.asList("Piscina, Gimnasio")));
        when(roomRepository.findAll()).thenReturn(mockedRooms);

        List<Room> actualRooms = roomService.getAllRooms();

        verify(roomRepository).findAll();
        assertEquals(mockedRooms, actualRooms);
    }

    @Test
    public void testGetRoomById() throws RoomNotFoundException {
        Integer roomId = 1;
        Room mockedRoom = new Room(1,
                "Ejemplo Room",
                "https://ejemplo.com/imagen.jpg",
                true,
                "Suite",
                2,
                new BigDecimal("150.00"),
                new BigDecimal("20.00"),
                new BigDecimal("170.00"),
                Arrays.asList("Toallas", "Champú", "Jabón"),
                Arrays.asList("Cama King Size", "Armario"),
                Arrays.asList("TV", "Minibar"),
                Arrays.asList("Desayuno incluido"),
                Arrays.asList("Wi-Fi gratuito"),
                Arrays.asList("Vistas panorámicas"),
                Arrays.asList("Piscina, Gimnasio"));
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(mockedRoom));

        Optional<Room> actualRoom = roomService.getRoomById(roomId);

        verify(roomRepository).findById(roomId);
        assertEquals(mockedRoom, actualRoom.get());
    }

    @Test
    public void testCreateRoom() {
        RoomDto roomDto = new RoomDto(1,
                "Room 1",
                "https://ejemplo.com/imagen.jpg",
                true,
                "Deluxe",
                2,
                new BigDecimal("100"),
                new BigDecimal("20.00"),
                new BigDecimal("170.00"),
                Arrays.asList("Toallas", "Champú", "Jabón"),
                Arrays.asList("Cama King Size", "Armario"),
                Arrays.asList("TV", "Minibar"),
                Arrays.asList("Desayuno incluido"),
                Arrays.asList("Wi-Fi gratuito"),
                Arrays.asList("Vistas panorámicas"),
                Arrays.asList("Piscina, Gimnasio"));

        roomService.createRoom(roomDto);

        ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(roomCaptor.capture());

        Room savedRoom = roomCaptor.getValue();

        assertEquals("Room 1", savedRoom.getName());
        assertTrue(savedRoom.getAvailable());
        assertEquals("Deluxe", savedRoom.getType());
        assertEquals(2, savedRoom.getCapacity());
        assertEquals(new BigDecimal(100), savedRoom.getInitialPrice());
    }

    @Test
    public void testUpdateRoom() throws RoomNotFoundException {
        Integer roomId = 1;
        Room existingRoom = new Room();
        existingRoom.setId(roomId);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(existingRoom));

        RoomDto roomDto = new RoomDto(1,
                "Updated Room",
                "https://ejemplo.com/imagen.jpg",
                false,
                "Suite",
                5,
                new BigDecimal("150"),
                new BigDecimal("20.00"),
                new BigDecimal("170.00"),
                Arrays.asList("Toallas", "Champú", "Jabón"),
                Arrays.asList("Cama King Size", "Armario"),
                Arrays.asList("TV", "Minibar"),
                Arrays.asList("Desayuno incluido"),
                Arrays.asList("Wi-Fi gratuito"),
                Arrays.asList("Vistas panorámicas"),
                Arrays.asList("Piscina, Gimnasio"));
        roomService.updateRoom(roomId, roomDto);

        ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(roomCaptor.capture());

        Room updatedRoom = roomCaptor.getValue();

        assertEquals(roomId, updatedRoom.getId());
        assertEquals("Updated Room", updatedRoom.getName());
        assertFalse(updatedRoom.getAvailable());
        assertEquals("Suite", updatedRoom.getType());
        assertEquals(5, updatedRoom.getCapacity());
        assertEquals(new BigDecimal(150), updatedRoom.getInitialPrice());
    }

    @Test
    public void testDeleteRoom() {
        Integer roomId = 1;
        Room room = new Room();
        room.setId(roomId);

        when(roomRepository.existsById(roomId)).thenReturn(true);

        roomService.deleteRoom(roomId);

        verify(roomRepository).deleteById(roomId);

        when(roomRepository.existsById(roomId)).thenReturn(false);
    }
}
