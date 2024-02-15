package com.ggomezr.bookingsystem.application.controller;

import com.ggomezr.bookingsystem.application.service.RoomService;
import com.ggomezr.bookingsystem.domain.dto.RoomDto;
import com.ggomezr.bookingsystem.domain.entity.Room;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RoomControllerTest {
    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @Test
    public void testGetAllRooms() {
        List<Room> expectedRooms = List.of(new Room(), new Room());
        when(roomService.getAllRooms()).thenReturn(expectedRooms);

        List<Room> actualRooms = roomController.getAllRooms();

        assertEquals(expectedRooms, actualRooms);
        verify(roomService).getAllRooms();
    }
    @Test
    public void testGetRoomById() throws Exception {
        Room expectedRoom = Room.builder().id(1).build();
        when(roomService.getRoomById(1)).thenReturn(Optional.of(expectedRoom));

        Optional<Room> actualRoom = roomController.getRoomById(1);

        assertTrue(actualRoom.isPresent());
        assertEquals(expectedRoom, actualRoom.get());
    }

    @Test
    public void testCreateRoom() {
        RoomDto newRoomDto = new RoomDto(1, "Room 1", "", true,
                "Single", 2, new BigDecimal(50),
                new BigDecimal(10), new BigDecimal(70),
                Arrays.asList("Toallas", "Champú", "Jabón"),
                Arrays.asList("Cama King Size", "Armario"),
                Arrays.asList("TV", "Minibar"),
                Arrays.asList("Desayuno incluido"),
                Arrays.asList("Wi-Fi gratuito"),
                Arrays.asList("Vistas panorámicas"),
                Arrays.asList("Piscina, Gimnasio"));

        roomController.createRoom(newRoomDto);

        verify(roomService).createRoom(eq(newRoomDto));
    }

    @Test
    public void testUpdateRoom() throws Exception {
        Integer roomId = 1;
        RoomDto updatedRoomDto = new RoomDto(1, "Room 1", "", true,
                "Single", 2, new BigDecimal(50),
                new BigDecimal(10), new BigDecimal(70),
                Arrays.asList("Toallas", "Champú", "Jabón"),
                Arrays.asList("Cama King Size", "Armario"),
                Arrays.asList("TV", "Minibar"),
                Arrays.asList("Desayuno incluido"),
                Arrays.asList("Wi-Fi gratuito"),
                Arrays.asList("Vistas panorámicas"),
                Arrays.asList("Piscina, Gimnasio"));

        Room existingRoom = Room.builder().id(roomId).build();

        when(roomService.getRoomById(roomId))
                .thenReturn(Optional.of(existingRoom));

        roomController.updateRoom(roomId, updatedRoomDto);

        verify(roomService).updateRoom(roomId, updatedRoomDto);
    }
    @Test
    public void testDeleteRoom() {
        roomController.deleteRoom(1);

        verify(roomService).deleteRoom(eq(1));
    }
}
