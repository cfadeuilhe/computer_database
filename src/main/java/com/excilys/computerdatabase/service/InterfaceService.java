package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.util.Consts;

public interface InterfaceService {

    public void delete(long id);

    public Company readOne(long id);

    public List<Entity> listEntities();

    public default void update(long id, Entity entity) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default void create(Entity entity) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default List<Entity> readPages(Page p) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default List<Entity> listSearch(String search) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default long countEntities(String search) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }
    
}
