package com.danarossa.database.daointerfaces;

import com.danarossa.entities.Entity;

import java.util.List;

/**
 * @param <E> entity type
 * @param <K>  type of entity's primary key
 */
public interface GenericDao<E extends Entity<K>, K> extends AutoCloseable {
    List<E> getAll();
    E getEntityById(K id);
    void update(E entity);
    void delete(K id);
    void insert(E entity);
    K getNextPrimaryKey();

}
