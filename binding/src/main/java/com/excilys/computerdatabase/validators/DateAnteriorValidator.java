package com.excilys.computerdatabase.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.excilys.computerdatabase.dto.ComputerDto;

public class DateAnteriorValidator implements ConstraintValidator<DateAnteriorValidation, ComputerDto> {
    @Autowired
    private MessageSource messageSource;
    @Override
    public void initialize(DateAnteriorValidation arg0) {

    }

    @Override
    public boolean isValid(ComputerDto computerDto, ConstraintValidatorContext context) {
        String format = messageSource.getMessage("util.date.format", null, LocaleContextHolder.getLocale());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String introducedDate = computerDto.getIntroducedDate();
        String discontinuedDate = computerDto.getDiscontinuedDate();

        String errorDateMessage = messageSource.getMessage("error.date", null, LocaleContextHolder.getLocale());
        String errorDateBeforeMessage = messageSource.getMessage("error.dateBefore", null, LocaleContextHolder.getLocale());
        String errorDateAfter1970Message = messageSource.getMessage("error.dateAfter1970", null, LocaleContextHolder.getLocale());

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        LocalDate dateIntro = null;
        LocalDate dateDisco = null;
        LocalDate date1970 = LocalDate.of(1970,1,2);

        if (introducedDate != null && discontinuedDate != null) {
            if (!introducedDate.isEmpty()) {
                if (GenericValidator.isDate(introducedDate, format, true)) {
                    dateIntro = LocalDate.parse(introducedDate, formatter);
                    if (!isAfter1970(dateIntro, date1970, context, errorDateAfter1970Message, "introduced")) {
                        isValid = false;
                    }
                } else {
                    context.buildConstraintViolationWithTemplate(errorDateMessage)
                            .addPropertyNode("introduced").addConstraintViolation();
                    isValid = false;
                }
            }

            if (!discontinuedDate.isEmpty()) {
                if (GenericValidator.isDate(discontinuedDate, format, true)) {
                    dateDisco = LocalDate.parse(discontinuedDate, formatter);
                    if (!isAfter1970(dateDisco, date1970, context, errorDateAfter1970Message, "discontinued")) {
                        isValid = false;
                    }
                } else {
                    context.buildConstraintViolationWithTemplate(errorDateMessage)
                            .addPropertyNode("discontinued").addConstraintViolation();
                    isValid = false;
                }
            }

            if (isValid && dateIntro != null && dateDisco != null) {
                if (!dateIntro.isBefore(dateDisco)) {
                    context.buildConstraintViolationWithTemplate(errorDateBeforeMessage)
                            .addPropertyNode("discontinued").addConstraintViolation();
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    private boolean isAfter1970(LocalDate date, LocalDate date1970, ConstraintValidatorContext context, String errorDateAfter1970Message, String dateType ) {
        if (date.isBefore(date1970)) {
            context.buildConstraintViolationWithTemplate(errorDateAfter1970Message)
                    .addPropertyNode(dateType).addConstraintViolation();
            return false;
        }
        return true;
    }

}
