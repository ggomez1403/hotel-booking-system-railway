package com.ggomezr.bookingsystem.domain.exceptions;

public class RoomNotFoundException extends Exception{
    public RoomNotFoundException(){
        super("Room not found");
    }
}
