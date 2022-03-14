package com.iharmolchan.meetingroomreservation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class DateIsAfterAnotherValidator implements ConstraintValidator<DateIsAfterAnother, Object> {

    private String baseDateField;
    private String matchDateField;

    @Override
    public void initialize(DateIsAfterAnother constraintAnnotation) {
        baseDateField = constraintAnnotation.baseDateField();
        matchDateField = constraintAnnotation.dateAfterField();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            LocalDateTime baseFieldValue = (LocalDateTime) getFieldValue(o, baseDateField);
            LocalDateTime matchFieldValue = (LocalDateTime) getFieldValue(o, matchDateField);
            return matchFieldValue.isAfter(baseFieldValue);
        } catch (Exception e) {
            return false;
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field passwordField = clazz.getDeclaredField(fieldName);
        passwordField.setAccessible(true);
        return passwordField.get(object);
    }
}
