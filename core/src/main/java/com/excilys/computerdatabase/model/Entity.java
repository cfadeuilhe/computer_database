package com.excilys.computerdatabase.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Entity {

    @Id
    @GeneratedValue
    protected long id;
    @Column
    protected String name;

    protected long getId() {
        return id;
    }
    protected void setId(long id) {
        this.id = id;
    }
    protected String getName() {
        return name;
    }
    protected void setName(String name) {
        this.name = name;
    }
}
