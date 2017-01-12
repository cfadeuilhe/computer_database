package com.excilys.computerdatabase.validators;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.util.Consts;

public class ComputerValidator {

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

    public static boolean dateOrderValidator(LocalDate introducedDate, LocalDate discontinuedDate) {
        if (introducedDate != null && discontinuedDate != null) {
            if (introducedDate.isAfter(discontinuedDate)) {
                return false;
            }
        }
        return true;
    }

    public static Map<String, String> validator(Computer c) {

        Map<String, String> map = new HashMap<String, String>();
        if (c.getId() != 0) {
            if (!ComputerValidator.idValidator(c.getId())) {
                map.put(Consts.NEGATIVE_ID, Consts.NEGATIVE_ID);
            }
        }
        if (!ComputerValidator.nameValidator(c.getName())) {
            map.put(Consts.NO_NAME, Consts.NO_NAME);
        }
        if (c.getName() != null) {
            if (!ComputerValidator.nameLengthValidator(c.getName())) {
                map.put(Consts.NAME_TOO_SHORT, Consts.NAME_TOO_SHORT);
            }
        }
        if (!ComputerValidator.dateOrderValidator(c.getIntroducedDate(), c.getDiscontinuedDate())) {
            map.put(Consts.DATE_ORDER_ERROR, Consts.DATE_ORDER_ERROR);
        }

        return map;
    }
}
