package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.util.List;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.util.Consts;

public interface InterfaceDao {

    public default List<Entity> read() {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default List<Entity> readPages(Page p) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default Entity readOne(long id) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default int create(Entity entity) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default void update(long id, Entity entity) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default void delete(Connection cn, long id) throws PersistenceException {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }
}
