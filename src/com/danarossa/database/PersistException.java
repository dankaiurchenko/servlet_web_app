package com.danarossa.database;

public class PersistException extends RuntimeException {
    public PersistException(Exception e) {
    }

    public PersistException(String message) {
        super(message);
    }

}
