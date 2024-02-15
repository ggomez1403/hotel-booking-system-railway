package com.ggomezr.bookingsystem.domain.exceptions;

public class UserNotAuthorizedException extends Exception{
    public UserNotAuthorizedException(String message){
        super(message);
    }
}
