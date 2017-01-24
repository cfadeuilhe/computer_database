package com.excilys.computerdatabase.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CompanyDto {

    private long id;
    @NotNull
    @Size(min = 2)
    private String name;
}
