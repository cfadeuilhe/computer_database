package com.excilys.computerdatabase.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.computerdatabase.mapper.DateConverter;
import com.excilys.computerdatabase.validators.ComputerValidator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Entity
@Table(name="computer")
public class Computer implements Serializable {

    private static final long serialVersionUID = -5186730060655040115L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name="introduced", nullable=true)
    @Convert(converter = DateConverter.class)
    @JsonSerialize(using=LocalDateSerializer.class)
    @JsonDeserialize(using=LocalDateDeserializer.class)
    private LocalDate introducedDate;

    @Column(name="discontinued", nullable=true)
    @Convert(converter = DateConverter.class)
    @JsonSerialize(using=LocalDateSerializer.class)
    @JsonDeserialize(using=LocalDateDeserializer.class)
    private LocalDate discontinuedDate;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="company_id", nullable=true)
    private Company company;

    private Computer(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.company = builder.company;
        this.introducedDate = builder.introducedDate;
        this.discontinuedDate = builder.discontinuedDate;
    }

    private Computer() {

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
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }
    public LocalDate getIntroducedDate() {
        return introducedDate;
    }
    public void setIntroducedDate(LocalDate introducedDate) {
        this.introducedDate = introducedDate;
    }
    public LocalDate getDiscontinuedDate() {
        return discontinuedDate;
    }
    public void setDiscontinuedDate(LocalDate discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    @Override
    public String toString() {
        return "\nComputer n" + id + ": [Name=" + name + ((company != null) ? (", Company=" + company.getName()) : "")
                + ((introducedDate != null) ? (", Introduced=" + introducedDate) : "")
                + ((discontinuedDate != null) ? (", Discontinued=" + discontinuedDate) : "") + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinuedDate == null) ? 0 : discontinuedDate.hashCode());
        result = prime * result + ((introducedDate == null) ? 0 : introducedDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Computer other = (Computer) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (company == null) {
            if (other.company != null)
                return false;
        } else if (!company.equals(other.company))
            return false;
        if (discontinuedDate == null) {
            if (other.discontinuedDate != null)
                return false;
        } else if (!discontinuedDate.equals(other.discontinuedDate))
            return false;
        if (introducedDate == null) {
            if (other.introducedDate != null)
                return false;
        } else if (!introducedDate.equals(other.introducedDate))
            return false;
        return true;
    }

    public static class ComputerBuilder {

        private long id;
        private String name;
        private Company company;
        private LocalDate introducedDate;
        private LocalDate discontinuedDate;

        public ComputerBuilder(String name) {
            this.name = name;
        }

        public ComputerBuilder() {

        }

        public ComputerBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ComputerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ComputerBuilder company(Company company) {
            this.company = company;
            return this;
        }

        public ComputerBuilder introducedDate(LocalDate introducedDate) {
            this.introducedDate = introducedDate;
            return this;
        }

        public ComputerBuilder discontinuedDate(LocalDate discontinuedDate) {
            this.discontinuedDate = discontinuedDate;
            return this;
        }

        public Computer build() {
            Computer c = new Computer(this);
            // Validation
            ComputerValidator.validator(c);
            return c;
        }
    }
}
