package com.excilys.computerdatabase.persistence;

import java.util.List;
import java.util.Map;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.util.Consts;

public interface InterfaceDao<T> {

    public default List<T> read(Map<String, String> orderMap){
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default T readOne(long id){
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default List<T> readPages(Page p, Map<String, String> orderMap){
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default void delete(long id) throws PersistenceException{
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default T readByName(String name){
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }
    
    public default int create(T entity) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }

    public default void update(long id, T entity) {
        throw new UnsupportedOperationException(Consts.METHOD_NOT_IMPLEMENTED);
    }
}
