package com.ggomezr.bookingsystem.application.service;

import com.ggomezr.bookingsystem.domain.dto.RoomDto;
import com.ggomezr.bookingsystem.domain.entity.Room;
import com.ggomezr.bookingsystem.domain.exceptions.RoomNotFoundException;
import com.ggomezr.bookingsystem.domain.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService{

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Integer id) throws RoomNotFoundException {
        return Optional.ofNullable(roomRepository.findById(id).orElseThrow(RoomNotFoundException::new));
    }

    public void createRoom(RoomDto roomDto) {
        Room room = Room.builder()
                .name(roomDto.name())
                .imgUrl(roomDto.imgUrl())
                .available(true)
                .type(roomDto.type())
                .capacity(roomDto.capacity())
                .initialPrice(roomDto.initialPrice())
                .taxesAndFees(roomDto.taxesAndFees())
                .totalPrice(roomDto.initialPrice().add(roomDto.taxesAndFees()))
                .bathroomAmenities(roomDto.bathroomAmenities())
                .bedroomAmenities(roomDto.bedroomAmenities())
                .entertainmentAmenities(roomDto.entertainmentAmenities())
                .foodAndDrinksAmenities(roomDto.foodAndDrinksAmenities())
                .internetAmenities(roomDto.internetAmenities())
                .moreAmenities(roomDto.moreAmenities())
                .highlights(roomDto.highlights())
                .build();
        roomRepository.save(room);
    }

    public void updateRoom(Integer id, RoomDto roomDto) throws RoomNotFoundException {
        Room existingRoom = roomRepository.findById(id).orElseThrow(RoomNotFoundException::new);

        existingRoom.setName(roomDto.name());
        existingRoom.setImgUrl(roomDto.imgUrl());
        existingRoom.setAvailable(roomDto.available());
        existingRoom.setType(roomDto.type());
        existingRoom.setCapacity(roomDto.capacity());
        existingRoom.setInitialPrice(roomDto.initialPrice());
        existingRoom.setTaxesAndFees(roomDto.taxesAndFees());
        existingRoom.setTotalPrice(roomDto.initialPrice().add(roomDto.taxesAndFees()));
        existingRoom.setBathroomAmenities(roomDto.bathroomAmenities());
        existingRoom.setBedroomAmenities(roomDto.bedroomAmenities());
        existingRoom.setEntertainmentAmenities(roomDto.entertainmentAmenities());
        existingRoom.setFoodAndDrinksAmenities(roomDto.foodAndDrinksAmenities());
        existingRoom.setInternetAmenities(roomDto.internetAmenities());
        existingRoom.setMoreAmenities(roomDto.moreAmenities());
        existingRoom.setHighlights(roomDto.highlights());

        roomRepository.save(existingRoom);
    }

    public void deleteRoom(Integer id) {
        roomRepository.deleteById(id);
    }
}
