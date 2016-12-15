package com.computer_database.persistence;

import java.sql.*;
import java.util.*;

import com.computer_database.model.Entity;
import com.computer_database.model.Page;

interface InterfaceDAO {

	public default List<Entity> read(Statement st) {
		throw new UnsupportedOperationException("Method create() is not implemented");
	}

	public default List<Entity> readPages(Statement st, Page p) {
		throw new UnsupportedOperationException("Method create() is not implemented");
	}

	public default Entity readOne(Statement st, long id) {
		throw new UnsupportedOperationException("Method create() is not implemented");
	}

	public default void create(Statement st, Entity entity) {
		throw new UnsupportedOperationException("Method create() is not implemented");
	}

	public default void update(Statement st, long id, Entity entity) {
		throw new UnsupportedOperationException("Method update() is not implemented");
	}

	public default void delete(Statement st, long id) {
		throw new UnsupportedOperationException("Method delete() is not implemented");
	}
}
