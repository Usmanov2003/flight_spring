package com.flight.flight_spring.exceptions;

public class TicketNotFound extends RuntimeException{
    public TicketNotFound(String message) { super(message);}
}
