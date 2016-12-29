package com.excilys.computerdatabase.dto;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.validators.ComputerValidator;

public class ComputerDto {

    private long id;
    private String name;
    private String introducedDate;
    private String discontinuedDate;
    private long companyId;
    private String companyName;
    
    private ComputerDto(ComputerDtoBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introducedDate = builder.introducedDate;
        this.discontinuedDate = builder.discontinuedDate;
        this.companyId = builder.companyId;
        this.companyName = builder.companyName;
    }

    public ComputerDto() {

    }

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

    public String getIntroducedDate() {
        return introducedDate;
    }

    public void setIntroducedDate(String introducedDate) {
        this.introducedDate = introducedDate;
    }

    public String getDiscontinuedDate() {
        return discontinuedDate;
    }

    public void setDiscontinuedDate(String discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "ComputerDto [id=" + id + ", name=" + name + ", introducedDate=" + introducedDate + ", discontinuedDate="
                + discontinuedDate + ", companyId=" + companyId + "]";
    }

    public static class ComputerDtoBuilder {

        private long id;
        private String name;
        private String introducedDate;
        private String discontinuedDate;
        private long companyId;
        private String companyName;
        
        public ComputerDtoBuilder(String name) {
            this.name = name;
        }

        public ComputerDtoBuilder() {

        }
        
        public ComputerDtoBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ComputerDtoBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public ComputerDtoBuilder introducedDate(String introducedDate) {
            this.introducedDate = introducedDate;
            return this;
        }

        public ComputerDtoBuilder discontinuedDate(String discontinuedDate) {
            this.discontinuedDate = discontinuedDate;
            return this;
        }
        
        public ComputerDtoBuilder companyId(long companyId) {
            this.companyId = companyId;
            return this;
        }

        public ComputerDtoBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public ComputerDto build() {
            ComputerDto c = new ComputerDto(this);
            return c;
        }
    }
}
