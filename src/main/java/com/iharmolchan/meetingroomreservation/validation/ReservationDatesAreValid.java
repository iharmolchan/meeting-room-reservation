package com.iharmolchan.meetingroomreservation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ReservationDatesValidator.class})
public @interface ReservationDatesAreValid {
    String message() default "Reservation start and finish dates should be in the same day." +
            " Reservation finish date should be after start date.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
