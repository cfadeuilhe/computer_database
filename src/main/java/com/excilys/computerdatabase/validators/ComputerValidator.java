package com.excilys.computerdatabase.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.util.Consts;

public class ComputerValidator {

    /*
     * Convention used : if return false -> error, true -> everything ok
     * 
     */

    public static boolean idValidator(long id) {
        if (id <= 0) {
            return false;
        }
        return true;
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
        if (name.length() <= 2) {
            return false;
        }
        return true;
    }

    public static boolean dateOrderValidator(LocalDate introducedDate, LocalDate discontinuedDate) {
        if (introducedDate != null && discontinuedDate != null) {
            if (introducedDate.isAfter(discontinuedDate)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isDateValidator(LocalDate introducedDate, LocalDate discontinuedDate) {
        //est ce que c'est une date lol
        return false;
    }

    public static List<String> validator(Computer c) {

        List<String> list = new ArrayList<>();
        if (c.getId() != 0) {
            if (!ComputerValidator.idValidator(c.getId())) {
                list.add(Consts.NEGATIVE_ID);
            }
        }
        if (!ComputerValidator.nameValidator(c.getName())) {
            list.add(Consts.NO_NAME);
        }
        if (!ComputerValidator.nameLengthValidator(c.getName())) {
            list.add(Consts.NAME_TOO_SHORT);
        }
        if (!ComputerValidator.dateOrderValidator(c.getIntroducedDate(), c.getDiscontinuedDate())) {
            list.add(Consts.DATE_ORDER_ERROR);
        }

        return list;
    }
}
