package com.flight.flight_spring.exceptions;

public class UserAlreadyRegistered extends RuntimeException{
    public UserAlreadyRegistered(String message) { super(message);}
}
