package com.excilys.computerdatabase.persistence;

import java.util.List;

import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;

public interface InterfaceDao {

    public default List<Entity> read() {
        throw new UnsupportedOperationException("Method create() is not implemented");
    }

    public default List<Entity> readPages(Page p) {
        throw new UnsupportedOperationException("Method create() is not implemented");
    }

    public default Entity readOne(long id) {
        throw new UnsupportedOperationException("Method create() is not implemented");
    }

    public default void create(Entity entity) {
        throw new UnsupportedOperationException("Method create() is not implemented");
    }

    public default void update(long id, Entity entity) {
        throw new UnsupportedOperationException("Method update() is not implemented");
    }

    public default void delete(long id) {
        throw new UnsupportedOperationException("Method delete() is not implemented");
    }
}
