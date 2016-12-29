package com.excilys.computerdatabase.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.util.Consts;

public class ComputerDtoValidator {
    private static List<String> list = new ArrayList<String>();

    public static void idValidator(int id) {
        if (id <= 0) {
            list.add(Consts.NEGATIVE_ID);
        }
    }

    public static void idValidator(String id) {

    }

    public static void nameValidator(String name) {
        if (name == null || name.length() <= 0) {
            list.add(Consts.NO_NAME);
        } else if (name.length() <= 2) {
            list.add(Consts.NAME_TOO_SHORT);
        }
    }

    public static void dateValidator(String introducedDate, String discontinuedDate) {
        // Check if the introduced date is before the discontinued date
        if (introducedDate != "" && discontinuedDate != "" && introducedDate != null && discontinuedDate != null) {
            if (LocalDate.parse(introducedDate).isAfter(LocalDate.parse(discontinuedDate))) {
                list.add(Consts.DATE_ORDER_ERROR);
            }
        }
    }

    public static List<String> validate(ComputerDto c) {
        // List<String> list = new ArrayList<String>();
        list.clear();

        ComputerDtoValidator.nameValidator(c.getName());
        ComputerDtoValidator.dateValidator(c.getIntroducedDate(), c.getDiscontinuedDate());

        return list;
    }
}
