package com.excilys.computerdatabase.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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

    public static boolean isANumber(String id) {
        return (id.matches("\\d+"));
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
        // DateFormat format = new SimpleDateFormat(DATE_FORMAT);

        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);

        if (date != null && date.length() > 0) {
            // format.setLenient(false);
            format.parse(date);
            return true;
        }
        return true;
    }

    public static Map<String, String> validate(ComputerDto c) {
        Map<String, String> map = new HashMap<String, String>();

        if (c.getId() != 0) {
            if (!ComputerDtoValidator.idValidator(c.getId())) {
                map.put(Consts.NEGATIVE_ID, Consts.NEGATIVE_ID);
            }
        }
        if (!ComputerDtoValidator.nameValidator(c.getName())) {
            map.put(Consts.NO_NAME, Consts.NO_NAME);
        }
        if (!ComputerDtoValidator.nameLengthValidator(c.getName())) {
            map.put(Consts.NAME_TOO_SHORT, Consts.NAME_TOO_SHORT);
        }
        if (!ComputerDtoValidator.dateOrderValidator(c.getIntroducedDate(), c.getDiscontinuedDate())) {
            map.put(Consts.DATE_ORDER_ERROR, Consts.DATE_ORDER_ERROR);
        }
        if (!ComputerDtoValidator.isDateValidator(c.getIntroducedDate())) {
            map.put(Consts.DATE_INTRO_FORMAT_ERROR, Consts.DATE_INTRO_FORMAT_ERROR);
        }
        if (!ComputerDtoValidator.isDateValidator(c.getDiscontinuedDate())) {
            map.put(Consts.DATE_DISCO_FORMAT_ERROR, Consts.DATE_DISCO_FORMAT_ERROR);
        }

        return map;
    }
}
