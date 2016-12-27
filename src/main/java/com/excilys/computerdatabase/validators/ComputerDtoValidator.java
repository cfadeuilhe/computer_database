package com.excilys.computerdatabase.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dto.ComputerDto;

public class ComputerDtoValidator {
    private static List<String> list = new ArrayList<String>();

    public static void idValidator(int id) {
        if (id <= 0) {
            list.add("NegativeId");
        }
    }

    public static void idValidator(String id) {

    }

    public static void nameValidator(String name) {
        if (name == null || name.length() <= 0) {
            list.add("NoName");
        } else if (name.length() <= 2) {
            list.add("NameTooShort");
        }
    }

    public static void dateValidator(String introducedDate, String discontinuedDate) {
        // Check if the introduced date is before the discontinued date
        if (introducedDate != "" && discontinuedDate != "" && introducedDate != null && discontinuedDate != null) {
            if (LocalDate.parse(introducedDate).isAfter(LocalDate.parse(discontinuedDate))) {
                list.add("DateOrderWrong");
            }
        }
    }

    public static List<String> validator(ComputerDto c) {
        // List<String> list = new ArrayList<String>();
        list.clear();

        ComputerDtoValidator.nameValidator(c.getName());
        ComputerDtoValidator.dateValidator(c.getIntroducedDate(), c.getDiscontinuedDate());

        return list;
    }
}
