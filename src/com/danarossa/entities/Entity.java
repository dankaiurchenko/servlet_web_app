package com.danarossa.entities;

import java.io.Serializable;

public class Entity<K> implements Serializable {
    protected K id;

    Entity() {
    }

    Entity(K id) {
        this.id = id;
    }

    public K getId() {
        return id;
    }

    public void setId(K id){
        this.id = id;
    }
}
