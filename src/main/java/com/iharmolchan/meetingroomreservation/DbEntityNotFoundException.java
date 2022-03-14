package com.iharmolchan.meetingroomreservation;

public class DbEntityNotFoundException extends RuntimeException{
    public DbEntityNotFoundException(String message) {
        super(message);
    }
}
