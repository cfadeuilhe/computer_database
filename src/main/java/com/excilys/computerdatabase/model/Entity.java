package com.excilys.computerdatabase.model;

public class Entity {
    protected long id;
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
