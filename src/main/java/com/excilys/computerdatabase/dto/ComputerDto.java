package com.excilys.computerdatabase.dto;

public class ComputerDto {
    private long id;
    private String name;
    private String introducedDate;
    private String discontinuedDate;
    private long companyId;

    public ComputerDto(String name, String introducedDate, String discontinuedDate, long companyId) {
        this.name = name;
        this.introducedDate = introducedDate;
        this.discontinuedDate = discontinuedDate;
        this.companyId = companyId;
    }
    
    public ComputerDto(long id, String name, String introducedDate, String discontinuedDate, long companyId) {
        this.id = id;
        this.name = name;
        this.introducedDate = introducedDate;
        this.discontinuedDate = discontinuedDate;
        this.companyId = companyId;
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

    @Override
    public String toString() {
        return "ComputerDto [id=" + id + ", name=" + name + ", introducedDate=" + introducedDate + ", discontinuedDate="
                + discontinuedDate + ", companyId=" + companyId + "]";
    }

}
