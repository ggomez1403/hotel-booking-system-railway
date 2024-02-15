package com.ggomezr.bookingsystem.domain.exceptions;

public class ReservationNotFoundException extends Exception{
    public ReservationNotFoundException(){
        super("Reservation not found");
    }
}
