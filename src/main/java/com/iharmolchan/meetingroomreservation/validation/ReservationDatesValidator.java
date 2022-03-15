package com.iharmolchan.meetingroomreservation.validation;

import com.iharmolchan.meetingroomreservation.model.Reservation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReservationDatesValidator implements ConstraintValidator<ReservationDatesAreValid, Reservation> {

    @Override
    public void initialize(ReservationDatesAreValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Reservation reservation, ConstraintValidatorContext constraintValidatorContext) {
        return reservation.getReservationFinish().isAfter(reservation.getReservationStart());
    }
}
