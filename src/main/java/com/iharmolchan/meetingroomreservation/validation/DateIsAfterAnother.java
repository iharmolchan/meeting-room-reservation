package com.iharmolchan.meetingroomreservation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DateIsAfterAnotherValidator.class})
public @interface DateIsAfterAnother {
    String message() default "Tested date in not after another one.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String baseDateField();
    String dateAfterField();
}
