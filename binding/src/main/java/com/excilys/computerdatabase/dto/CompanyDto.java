package com.excilys.computerdatabase.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CompanyDto {

    private long id;
    @NotNull
    @Size(min = 2)
    private String name;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
