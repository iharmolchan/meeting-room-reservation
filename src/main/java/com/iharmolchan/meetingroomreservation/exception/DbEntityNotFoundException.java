package com.iharmolchan.meetingroomreservation.exception;

public class DbEntityNotFoundException extends RuntimeException{
    public DbEntityNotFoundException(String message) {
        super(message);
    }
}
