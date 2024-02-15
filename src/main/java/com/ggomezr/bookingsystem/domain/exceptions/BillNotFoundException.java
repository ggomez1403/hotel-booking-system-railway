package com.ggomezr.bookingsystem.domain.exceptions;

public class BillNotFoundException extends Exception{
    public BillNotFoundException(){
        super("Bill not found");
    }
}
