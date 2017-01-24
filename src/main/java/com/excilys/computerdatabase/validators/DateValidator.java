package com.excilys.computerdatabase.validators;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateValidation, String> {

    @Override
    public void initialize(DateValidation arg0) {

    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext ctx) {

        if (date != null && date.length() > 0) {
            try {
                LocalDate myDate = LocalDate.parse(date);
                if (myDate.getYear() < 1970) {
                    return false;
                }
            } catch (Exception exception) {
                return false;
            }
        }
        return true;
    }

}
