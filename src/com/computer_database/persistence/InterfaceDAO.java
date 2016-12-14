package com.computer_database.persistence;

import java.sql.*;
import java.util.*;

import com.computer_database.model.Entity;
import com.computer_database.model.Page;

interface InterfaceDAO {
	
	public List<Entity> read(Statement st);
	public List<Entity> readPages(Statement st, Page p);
	public Entity readOne(Statement st, long id);
	public void create(Statement st, Entity entity);
	public void update(Statement st, long id, Entity entity);
	public void delete(Statement st, long id);
}
