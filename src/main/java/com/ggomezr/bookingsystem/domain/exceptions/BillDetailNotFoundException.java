package com.ggomezr.bookingsystem.domain.exceptions;

public class BillDetailNotFoundException extends Exception{
    public BillDetailNotFoundException(){
        super("Bill detail not found");
    }
}
