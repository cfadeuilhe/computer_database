package com.excilys.computerdatabase.validators;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.util.Consts;

public class ComputerDtoValidator {

    final static String DATE_FORMAT = "yyyy-MM-dd";

    /*
     * Convention used : if return false -> error, true -> everything ok
     * 
     */

    public static boolean idValidator(long id) {
        return (id > 0);
    }

    public static void idValidator(String id) {

    }

    public static boolean nameValidator(String name) {
        if (name == null || name.length() <= 0) {
            return false;
        }
        return true;
    }

    public static boolean nameLengthValidator(String name) {
        return (name.length() > 2);
    }

    public static boolean dateOrderValidator(String introducedDate, String discontinuedDate) {
        // Check if the introduced date is before the discontinued date
        if (introducedDate != null && discontinuedDate != null && !introducedDate.isEmpty()
                && !discontinuedDate.isEmpty()) {
            if (LocalDate.parse(introducedDate).isAfter(LocalDate.parse(discontinuedDate))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDateValidator(String date) {
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);

        if (date != null && date.length() > 0) {
            try {
                format.setLenient(false);
                format.parse(date);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
        return true;
    }

    public static List<String> validate(ComputerDto c) {
        List<String> list = new ArrayList<>();

        if (c.getId() != 0) {
            if (!ComputerDtoValidator.idValidator(c.getId())) {
                list.add(Consts.NEGATIVE_ID);
            }
        }
        if (!ComputerDtoValidator.nameValidator(c.getName())) {
            list.add(Consts.NO_NAME);
        }
        if (!ComputerDtoValidator.nameLengthValidator(c.getName())) {
            list.add(Consts.NAME_TOO_SHORT);
        }
        if (!ComputerDtoValidator.dateOrderValidator(c.getIntroducedDate(), c.getDiscontinuedDate())) {
            list.add(Consts.DATE_ORDER_ERROR);
        }
        if(!ComputerDtoValidator.isDateValidator(c.getIntroducedDate())){
            list.add(Consts.DATE_INTRO_FORMAT_ERROR);
        }
        if(!ComputerDtoValidator.isDateValidator(c.getDiscontinuedDate())){
            list.add(Consts.DATE_DISCO_FORMAT_ERROR);
        }

        return list;
    }
}
