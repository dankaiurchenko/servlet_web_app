package com.danarossa.controllers;

public class ControllerException extends RuntimeException {
    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
