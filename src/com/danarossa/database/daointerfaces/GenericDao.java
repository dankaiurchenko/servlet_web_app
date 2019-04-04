package com.danarossa.database.daointerfaces;

import com.danarossa.entities.Entity;

import java.util.List;

public interface GenericDao<E extends Entity<K>, K> {
    public abstract List<E> getAll();
    public abstract E getEntityById(K id);
    public abstract void update(E entity);
    public abstract void delete(K id);
    public abstract void insert(E entity);
    public abstract K getNextPrimaryKey();

}
