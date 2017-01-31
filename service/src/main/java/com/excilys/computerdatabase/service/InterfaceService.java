package com.excilys.computerdatabase.service;

import java.util.List;
import java.util.Map;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.util.Consts;

public interface InterfaceService<T> {

    public void delete(long id) throws PersistenceException;

    public T readOne(long id);

    public List<T> listEntities(Map<String, String> orderMap);

    public default void update(long id, T entity) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default int create(T entity) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default List<T> readPages(Page p, Map<String, String> orderMap) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default List<T> listSearch(String search, Map<String, String> orderMap) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default long countEntities(String search) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }
    
}
