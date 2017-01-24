package com.excilys.computerdatabase.persistence;

import java.util.List;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.util.Consts;

public interface InterfaceDao {

    public List<Entity> read();

    public Entity readOne(long id);

    public List<Entity> readPages(Page p);

    public void delete(long id) throws PersistenceException;

    public default int create(Entity entity) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default void update(long id, Entity entity) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }
}
