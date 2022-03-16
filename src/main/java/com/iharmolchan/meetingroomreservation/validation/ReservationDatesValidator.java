package com.iharmolchan.meetingroomreservation.validation;

import com.iharmolchan.meetingroomreservation.model.Reservation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReservationDatesValidator implements ConstraintValidator<ReservationDatesAreValid, Reservation> {

    @Override
    public void initialize(ReservationDatesAreValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Reservation reservation, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate reservationStartDate = reservation.getReservationStart().toLocalDate();
        LocalDate reservationFinishDate = reservation.getReservationFinish().toLocalDate();
        return reservationStartDate.isEqual(reservationFinishDate) &&
                reservation.getReservationFinish().isAfter(reservation.getReservationStart());
    }
}
